package com.banco.austro.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Evento para operaciones de concatenación (bloqueante)
 * Contiene información sobre la operación realizada
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConcatenationEvent {
    
    /**
     * Parámetros utilizados en la concatenación
     */
    private List<String> parameters;
    
    /**
     * Resultado de la concatenación
     */
    private String result;
    
    /**
     * Indica si la operación fue exitosa
     */
    private boolean success;
    
    /**
     * Mensaje de error si la operación falló
     */
    private String errorMessage;
    
    /**
     * Timestamp de cuando ocurrió el evento
     */
    private LocalDateTime timestamp;
    
    /**
     * Estrategia de validación utilizada
     */
    private String validationStrategy;
    
    /**
     * Constructor para eventos exitosos
     */
    public static ConcatenationEvent success(List<String> parameters, String result, String strategy) {
        return new ConcatenationEvent(parameters, result, true, null, LocalDateTime.now(), strategy);
    }
    
    /**
     * Constructor para eventos fallidos
     */
    public static ConcatenationEvent failure(List<String> parameters, String errorMessage, String strategy) {
        return new ConcatenationEvent(parameters, null, false, errorMessage, LocalDateTime.now(), strategy);
    }
}