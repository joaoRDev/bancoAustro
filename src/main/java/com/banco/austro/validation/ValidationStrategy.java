package com.banco.austro.validation;

public interface ValidationStrategy {
    ValidationResult validate(String input);
}