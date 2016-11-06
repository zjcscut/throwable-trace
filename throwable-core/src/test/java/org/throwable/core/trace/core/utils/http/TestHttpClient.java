package org.throwable.core.trace.core.utils.http;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zjc
 * @version 2016/11/6 11:16
 * @description
 */
public class TestHttpClient {

	@Test
	public void test1() throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("id", "1");
		params.put("name", "zjcscut");
		String url = "http://localhost:7003/user/json";
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 10000; i++) {
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					String result = HttpClientUtils.getInstance().
							doPost(url, params)
							.getContent();
					System.out.println(result);
				}
			});
		}

       executorService.shutdown();

		System.in.read();
	}
}
