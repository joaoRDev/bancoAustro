package com.banco.austro.sanitizer;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SqlInjectionSanitizer implements InputSanitizer {
    
    @Override
    public String sanitize(String input) {
        return input.replaceAll("[';\"\\-\\-/\\*]", "").trim();
    }
}