package com.banco.austro.service;

import com.banco.austro.validation.ValidationStrategy;
import com.banco.austro.validation.ValidationResult;
import com.banco.austro.sanitizer.SanitizerFactory;
import com.banco.austro.interceptor.Logged;
import com.banco.austro.events.ConcatenationEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import java.util.List;

/**
 * Servicio para operaciones de concatenación de parámetros
 * Implementa validación, sanitización y concatenación de strings
 * Emite eventos síncronos para auditoría
 */
@ApplicationScoped
@Logged
public class ConcatenationService {
    
    @Inject
    SanitizerFactory sanitizerFactory;
    
    @Inject
    Event<ConcatenationEvent> concatenationEvent;
    
    /**
     * Procesa y concatena una lista de parámetros aplicando validaciones
     * @param params Lista de parámetros a concatenar
     * @param validators Lista de estrategias de validación a aplicar
     * @return Resultado del servicio con la concatenación o error
     */
    public ServiceResult<String> processAndConcatenate(List<String> params, List<ValidationStrategy> validators) {
        
        String strategyNames = validators.stream()
            .map(v -> v.getClass().getSimpleName())
            .reduce((a, b) -> a + "," + b)
            .orElse("none");
            
        for (String param : params) {
            for (ValidationStrategy validator : validators) {
                ValidationResult result = validator.validate(param);
                if (!result.isValid()) {
                    // Emitir evento de fallo (bloqueante)
                    concatenationEvent.fire(ConcatenationEvent.failure(params, result.getMessage(), strategyNames));
                    return ServiceResult.<String>failure(result.getMessage());
                }
            }
        }
        
        StringBuilder result = new StringBuilder();
        var sanitizer = sanitizerFactory.getSqlInjectionSanitizer();
        
        for (String param : params) {
            result.append(sanitizer.sanitize(param));
        }
        
        String finalResult = result.toString();
        
        // Emitir evento de éxito (bloqueante)
        concatenationEvent.fire(ConcatenationEvent.success(params, finalResult, strategyNames));
        
        return ServiceResult.<String>success(finalResult);
    }
}