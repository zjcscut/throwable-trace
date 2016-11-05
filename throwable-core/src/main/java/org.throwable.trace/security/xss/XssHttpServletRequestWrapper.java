package org.throwable.trace.security.xss;

import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author zjc
 * @version 2016/10/17 22:57
 * @description xss过滤包装器
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

	public XssHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
	}

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

	private String cleanXSS(String val) {
		return HtmlUtils.htmlEscape(val);
	}
}
