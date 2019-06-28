package com.cg.utils.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 自定义金额验证注解
 * @author Rex.Tan
 * 2019年3月25日 下午3:44:19
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=AmountValidator.class)
public @interface Amount {

    String message() default "金额超出范围{11,2}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}