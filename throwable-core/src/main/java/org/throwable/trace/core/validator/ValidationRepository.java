package org.throwable.trace.core.validator;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author zjc
 * @version 2016/11/30 0:16
 * @description 校验结果仓库
 */

public class ValidationRepository extends ArrayList<ValidationRepository.ValidationResult> {

	private boolean success = true;

	@Override
	public boolean add(ValidationResult validationResult) {
		success = false;
		return super.add(validationResult);
	}

	@Override
	public boolean addAll(Collection<? extends ValidationResult> c) {
		success = false;
		return super.addAll(c);
	}

	public boolean addResult(String template, String message) {
		return add(new ValidationResult(template, message));
	}

	/**
	 * 校验结果
	 */
	public class ValidationResult {

		private String template;
		private String message;

		public ValidationResult() {
		}

		public ValidationResult(String template, String message) {
			this.template = template;
			this.message = message;
		}

		public String getTemplate() {
			return template;
		}

		public void setTemplate(String template) {
			this.template = template;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		@Override
		public String toString() {
			return String.format("%s:%s", template, message);
		}
	}

	public boolean isSuccess() {
		return success;
	}
}
