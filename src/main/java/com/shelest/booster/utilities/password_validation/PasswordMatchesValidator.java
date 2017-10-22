package com.shelest.booster.utilities.password_validation;

import com.shelest.booster.utilities.dto.DeveloperDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;



public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final DeveloperDTO developerDTO = (DeveloperDTO) obj;
        return developerDTO.getPassword().equals(developerDTO.getMatchingPassword());
    }

}