package org.throwable.trace.core.utils.stream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author zhangjinci
 * @version 2016/11/22 17:15
 * @function 流处理工具类
 */
public final class StreamsUtils {

    private static final int DEFAULT_BUFFER_SIZE = 8192;

    private StreamsUtils() {
    }

    public static long copy(InputStream inputStream, OutputStream outputStream, boolean closeOutputStream) throws IOException {
        return copy(inputStream, outputStream, closeOutputStream, new byte[DEFAULT_BUFFER_SIZE]);
    }

    public static long copy(InputStream inputStream, OutputStream outputStream, boolean closeOutputStream, byte[] buffer) throws IOException {
        OutputStream out = outputStream;
        InputStream in = inputStream;

        try {
            long total = 0L;

            while (true) {
                int res = in.read(buffer);
                if (res == -1) {
                    if (out != null) {
                        if (closeOutputStream) {
                            out.close();
                        } else {
                            out.flush();
                        }
                        out = null;
                    }

                    in.close();
                    in = null;
                    long res1 = total;
                    return res1;
                }

                if (res > 0) {
                    total += (long) res;
                    if (out != null) {
                        out.write(buffer, 0, res);
                    }
                }
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (closeOutputStream) {
                if (out != null) {
                    out.close();
                }
            }
        }
    }

    public static String asString(InputStream inputStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        copy(inputStream, baos, true);
        return baos.toString();
    }

    public static String asString(InputStream inputStream, String encoding) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        copy(inputStream, baos, true);
        return baos.toString(encoding);
    }

    public static String checkFileName(String fileName) {
        if (fileName != null && fileName.indexOf(0) != -1) {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < fileName.length(); ++i) {
                char c = fileName.charAt(i);
                switch (c) {
                    case '\u0000':
                        sb.append("\\0");
                        break;
                    default:
                        sb.append(c);
                }
            }
            throw new RuntimeException(fileName + " Invalid file name: " + sb);
        } else {
            return fileName;
        }
    }
}
