package com.banco.austro.validation;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SqlInjectionValidationStrategy implements ValidationStrategy {
    
    private static final String[] SQL_KEYWORDS = {
        "SELECT", "INSERT", "UPDATE", "DELETE", "DROP", "CREATE", "ALTER", 
        "EXEC", "UNION", "SCRIPT", "--", "/*", "*/", "XP_", "SP_"
    };
    
    @Override
    public ValidationResult validate(String input) {
        String upperInput = input.toUpperCase();
        for (String keyword : SQL_KEYWORDS) {
            if (upperInput.contains(keyword)) {
                return ValidationResult.failure("Entrada contiene caracteres no permitidos");
            }
        }
        return ValidationResult.success();
    }
}