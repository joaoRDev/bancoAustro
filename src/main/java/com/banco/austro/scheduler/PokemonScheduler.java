package com.banco.austro.scheduler;

import com.banco.austro.service.PokemonService;
import com.banco.austro.service.ServiceResult;
import com.banco.austro.dto.MoveResponse;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

@ApplicationScoped
public class PokemonScheduler {
    
    private static final Logger LOG = Logger.getLogger(PokemonScheduler.class);
    
    @Inject
    PokemonService pokemonService;
    
    @ConfigProperty(name = "pokemon.scheduler.strategy", defaultValue = "retry")
    String defaultStrategy;
    
    @Scheduled(cron = "{pokemon.scheduler.cron}")
    public void scheduledPokemonCall() {
        LOG.info("Ejecutando tarea periódica para obtener movimientos de Pokemon");
        
        ServiceResult<MoveResponse> result = pokemonService.getMoves(defaultStrategy, true);
        
        if (result.isSuccess()) {
            MoveResponse data = result.getData();
            LOG.infof("Tarea periódica completada exitosamente. Movimientos obtenidos: %d", 
                     data.getCount());
        } else {
            LOG.errorf("Error en tarea periódica: %s", result.getErrorMessage());
        }
    }
}