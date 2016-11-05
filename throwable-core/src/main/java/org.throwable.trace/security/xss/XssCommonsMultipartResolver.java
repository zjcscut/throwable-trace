package org.throwable.trace.security.xss;

import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zjc
 * @version 2016/10/17 23:10
 * @description 具有xss功能的multipart解析器
 */
public class XssCommonsMultipartResolver extends CommonsMultipartResolver {

	public XssCommonsMultipartResolver() {
	}

	@Override
	public MultipartHttpServletRequest resolveMultipart(HttpServletRequest request) throws MultipartException {
		MultipartParsingResult parsingResult = super.parseRequest(request);
		return new DefaultMultipartHttpServletRequest(request, parsingResult.getMultipartFiles(),
				parsingResult.getMultipartParameters(), parsingResult.getMultipartParameterContentTypes()) {
			@Override
			public String getHeader(String name) {
				String val = super.getHeader(name);
				return val == null ? null : cleanXSS(val);
			}

			@Override
			public String getParameter(String name) {
				String val = super.getParameter(name);
				return val == null ? null : cleanXSS(val);
			}

			@Override
			public String[] getParameterValues(String name) {
				String[] vals = super.getParameterValues(name);
				if (vals == null || vals.length == 0) {
					return null;
				}
				String[] result = new String[vals.length];
				for (int i = 0; i < result.length; i++) {
					result[i] = cleanXSS(vals[i]);
				}
				return result;
			}
		};
	}

	private String cleanXSS(String val) {
		return HtmlUtils.htmlEscape(val);
	}
}
