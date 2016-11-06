package org.throwable.trace.core.security.xss;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;
import org.springframework.web.util.HtmlUtils;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author zjc
 * @version 2016/10/17 23:36
 * @description 解决@PathVariable注解造成的xss攻击问题,注意:此类必需由WebApplicationContext初始化
 */
public class XssHandlerMappingPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String s) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String s) throws BeansException {
		if (bean instanceof AbstractHandlerMapping) {
			AbstractHandlerMapping ahm = (AbstractHandlerMapping) bean;
			ahm.setUrlPathHelper(new XssUrlPathHelper());
		}
		return bean;
	}

	public static class XssUrlPathHelper extends UrlPathHelper {
		@Override
		public Map<String, String> decodePathVariables(HttpServletRequest request, Map<String, String> vars) {
			Map<String, String> result = super.decodePathVariables(request, vars);
			if (!CollectionUtils.isEmpty(result)) {
				for (String key : result.keySet()) {
					result.put(key, cleanXSS(result.get(key)));
				}
			}
			return result;
		}

		@Override
		public MultiValueMap<String, String> decodeMatrixVariables(HttpServletRequest request, MultiValueMap<String, String> vars) {
			MultiValueMap<String, String> mvm = super.decodeMatrixVariables(request, vars);
			if (!CollectionUtils.isEmpty(mvm)) {
				for (String key : mvm.keySet()) {
					List<String> vals = mvm.get(key);
					for (int i = 0; i < vals.size(); i++) {
						vals.set(i, cleanXSS(vals.get(i)));
					}
				}
			}
			return mvm;
		}

		private String cleanXSS(String val) {
			return HtmlUtils.htmlEscape(val);
		}
	}
}
