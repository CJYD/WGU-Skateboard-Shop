package com.example.demo.validators;

import com.example.demo.domain.Part;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class InventoryRangeValidator implements ConstraintValidator<ValidInventoryRange, Part> {

    /* @Override
    public boolean isValid(Part part, ConstraintValidatorContext context) {
        if (part.getInv() < part.getMinInv() || part.getInv() > part.getMaxInv()) {
            return false;
        }
        else {
            return true;
        }
    }
    */
    @Override
    public boolean isValid(Part part, ConstraintValidatorContext context ) {
        boolean isValid = true;

        if (part.getInv() < part.getMinInv()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Inventory cannot be lower than the minimum inventory.")
                    .addPropertyNode("inv")
                    .addConstraintViolation();
            isValid = false;
        }
        if (part.getInv() > part.getMaxInv()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Inventory cannot be higher than the maximum inventory.")
                    .addPropertyNode("inv")
                    .addConstraintViolation();
            isValid = false;
        }
        return isValid;
    }
}