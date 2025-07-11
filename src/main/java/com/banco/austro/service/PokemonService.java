package com.banco.austro.service;

import com.banco.austro.dto.MoveResponse;
import com.banco.austro.faulttolerance.FaultToleranceFactory;
import com.banco.austro.faulttolerance.FaultToleranceStrategy;
import com.banco.austro.interceptor.Logged;
import com.banco.austro.events.PokemonApiEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import io.smallrye.mutiny.Uni;

/**
 * Servicio para operaciones con Pokemon API
 * Implementa tolerancia a fallos y emite eventos asíncronos
 * Utiliza Mutiny para operaciones reactivas
 */
@ApplicationScoped
@Logged
public class PokemonService {
    
    private static final Logger LOG = Logger.getLogger(PokemonService.class);
    
    @Inject
    FaultToleranceFactory faultToleranceFactory;
    
    @Inject
    Event<PokemonApiEvent> pokemonApiEvent;
    
    /**
     * Obtiene movimientos de Pokemon usando estrategia de tolerancia a fallos
     * @param strategy Estrategia a utilizar (retry, circuitbreaker)
     * @return Resultado del servicio con los movimientos o error
     */
    public ServiceResult<MoveResponse> getMoves(String strategy) {
        return getMoves(strategy, false);
    }
    
    /**
     * Obtiene movimientos de Pokemon con indicador de origen
     * @param strategy Estrategia a utilizar
     * @param fromScheduler Indica si la llamada proviene del scheduler
     * @return Resultado del servicio
     */
    public ServiceResult<MoveResponse> getMoves(String strategy, boolean fromScheduler) {
        long startTime = System.currentTimeMillis();
        
        try {
            FaultToleranceStrategy faultStrategy = getStrategy(strategy);
            MoveResponse response = faultStrategy.execute();
            long responseTime = System.currentTimeMillis() - startTime;
            
            LOG.infof("Successfully retrieved moves using %s strategy", strategy);
            
            // Emitir evento de éxito (no bloqueante)
            pokemonApiEvent.fireAsync(PokemonApiEvent.success(strategy, response.getCount(), responseTime, fromScheduler));
            
            return ServiceResult.success(response);
        } catch (Exception e) {
            long responseTime = System.currentTimeMillis() - startTime;
            
            LOG.errorf("Error retrieving moves with %s strategy: %s", strategy, e.getMessage());
            
            // Emitir evento de fallo (no bloqueante)
            pokemonApiEvent.fireAsync(PokemonApiEvent.failure(strategy, e.getMessage(), responseTime, fromScheduler));
            
            return ServiceResult.failure("Error al obtener movimientos: " + e.getMessage());
        }
    }
    
    private FaultToleranceStrategy getStrategy(String strategy) {
        return switch (strategy.toLowerCase()) {
            case "retry" -> faultToleranceFactory.getRetryStrategy();
            case "circuitbreaker" -> faultToleranceFactory.getCircuitBreakerStrategy();
            default -> faultToleranceFactory.getRetryStrategy();
        };
    }
}