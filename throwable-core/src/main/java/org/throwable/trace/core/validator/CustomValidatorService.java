package org.throwable.trace.core.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author zjc
 * @version 2016/11/30 0:26
 * @description 自定义校验服务类
 */
@Service
public class CustomValidatorService {

	@Autowired(required = false)
	private Validator validator;

	/**
	 * 对bean进行校验
	 * @param bean bean
	 * @return ValidationRepository
	 */
	public <T> ValidationRepository validate(T bean) {
		Set<ConstraintViolation<T>> constraintViolations = initValidationService(validator).validate(bean);
		ValidationRepository repository = new ValidationRepository();
		if (!constraintViolations.isEmpty()) {
			for (ConstraintViolation<T> constraintViolation : constraintViolations) {
				repository.addResult(constraintViolation.getMessageTemplate(), constraintViolation.getMessage());
			}
		}
		return repository;
	}

	private Validator initValidationService(Validator validator){
		Validator validatorX = validator;
		if (validatorX == null){
			validatorX = Validation.buildDefaultValidatorFactory().getValidator();
		}
		return validatorX;
	}
}
