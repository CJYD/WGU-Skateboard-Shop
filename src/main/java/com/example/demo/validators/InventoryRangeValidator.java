package com.example.demo.validators;

import com.example.demo.domain.Part;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class InventoryRangeValidator implements ConstraintValidator<ValidInventoryRange, Part> {

    @Override
    public boolean isValid(Part part, ConstraintValidatorContext context) {
        if (part.getInv() < part.getMinInv() || part.getInv() > part.getMaxInv()) {
            return false;
        }
        else {
            return true;
        }
    }
}