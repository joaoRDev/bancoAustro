package com.banco.austro.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Evento para operaciones de Pokemon API (no bloqueante)
 * Contiene información sobre las llamadas a la API externa
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PokemonApiEvent {
    
    /**
     * Estrategia de tolerancia a fallos utilizada
     */
    private String faultToleranceStrategy;
    
    /**
     * Número de movimientos obtenidos
     */
    private int movesCount;
    
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
     * Tiempo de respuesta en milisegundos
     */
    private long responseTime;
    
    /**
     * Indica si fue llamada desde el scheduler
     */
    private boolean fromScheduler;
    
    /**
     * Constructor para eventos exitosos
     */
    public static PokemonApiEvent success(String strategy, int count, long responseTime, boolean fromScheduler) {
        return new PokemonApiEvent(strategy, count, true, null, LocalDateTime.now(), responseTime, fromScheduler);
    }
    
    /**
     * Constructor para eventos fallidos
     */
    public static PokemonApiEvent failure(String strategy, String errorMessage, long responseTime, boolean fromScheduler) {
        return new PokemonApiEvent(strategy, 0, false, errorMessage, LocalDateTime.now(), responseTime, fromScheduler);
    }
}