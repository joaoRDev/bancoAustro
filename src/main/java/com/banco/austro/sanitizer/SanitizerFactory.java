package com.banco.austro.sanitizer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class SanitizerFactory {
    
    @Inject
    SqlInjectionSanitizer sqlInjectionSanitizer;
    
    public InputSanitizer getSqlInjectionSanitizer() {
        return sqlInjectionSanitizer;
    }
}