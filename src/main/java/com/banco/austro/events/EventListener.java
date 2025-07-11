package com.banco.austro.events;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import org.jboss.logging.Logger;

/**
 * Listener para eventos del sistema
 * Procesa eventos síncronos y asíncronos para auditoría y monitoreo
 */
@ApplicationScoped
public class EventListener {
    
    private static final Logger LOG = Logger.getLogger(EventListener.class);
    
    /**
     * Procesa eventos de concatenación (síncronos/bloqueantes)
     * @param event Evento de concatenación
     */
    public void onConcatenationEvent(@Observes ConcatenationEvent event) {
        if (event.isSuccess()) {
            LOG.infof("[AUDIT] Concatenación exitosa - Parámetros: %d, Resultado: %s, Estrategia: %s", 
                     event.getParameters().size(), 
                     event.getResult(), 
                     event.getValidationStrategy());
        } else {
            LOG.warnf("[AUDIT] Concatenación fallida - Parámetros: %d, Error: %s, Estrategia: %s", 
                     event.getParameters().size(), 
                     event.getErrorMessage(), 
                     event.getValidationStrategy());
        }
    }
    
    /**
     * Procesa eventos de Pokemon API (asíncronos/no bloqueantes)
     * @param event Evento de Pokemon API
     */
    public void onPokemonApiEvent(@Observes PokemonApiEvent event) {
        if (event.isSuccess()) {
            LOG.infof("[MONITOR] Pokemon API exitosa - Movimientos: %d, Estrategia: %s, Tiempo: %dms, Scheduler: %s", 
                     event.getMovesCount(), 
                     event.getFaultToleranceStrategy(), 
                     event.getResponseTime(),
                     event.isFromScheduler());
        } else {
            LOG.errorf("[MONITOR] Pokemon API fallida - Error: %s, Estrategia: %s, Tiempo: %dms, Scheduler: %s", 
                      event.getErrorMessage(), 
                      event.getFaultToleranceStrategy(), 
                      event.getResponseTime(),
                      event.isFromScheduler());
        }
    }
}