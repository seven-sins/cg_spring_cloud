package com.cg.utils.validator;

import java.math.BigDecimal;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义金额验证器
 * @author Rex.Tan
 * 2019年3月25日 下午3:44:34
 */
public class AmountValidator implements ConstraintValidator<Amount, BigDecimal> {

    /**
     * 表示金额的正则表达式
     */
    private static String REGEX = "^(\\-{0,1})\\d{1,11}(\\.\\d{1,2})?$";
    private static Pattern PATTERN = Pattern.compile(REGEX);

    @Override
    public void initialize(Amount amount) {

    }

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext validatorContext) {
    	if(value == null) {
    		return true;
    	}
        return PATTERN.matcher(value.toString()).matches();
    }

}
