package com.banco.austro.validation;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class NullBlankValidationStrategy implements ValidationStrategy {
    
    @Override
    public ValidationResult validate(String input) {
        if (input == null || input.trim().isEmpty()) {
            return ValidationResult.failure("Parámetro no puede ser nulo o vacío");
        }
        return ValidationResult.success();
    }
}