package ir.mvbdx.mywallet.validator;

import org.apache.commons.beanutils.PropertyUtils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AtLeastOneNotNullValidator implements ConstraintValidator<AtLeastOneNotNull, Object> {

    private String[] fieldNames;

    public void initialize(AtLeastOneNotNull constraintAnnotation) {
        this.fieldNames = constraintAnnotation.fieldNames();
    }

    public boolean isValid(Object object, ConstraintValidatorContext constraintContext) {
        if (object == null) return true;
        try {
            for (String fieldName : fieldNames) {
                Object property = PropertyUtils.getProperty(object, fieldName);
                if (property != null) return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}