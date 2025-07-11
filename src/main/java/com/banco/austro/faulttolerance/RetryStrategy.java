package com.banco.austro.faulttolerance;

import com.banco.austro.client.PokemonApiClient;
import com.banco.austro.dto.MoveResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class RetryStrategy implements FaultToleranceStrategy {
    
    @Inject
    @RestClient
    PokemonApiClient pokemonApiClient;
    
    @Override
    @Retry(maxRetries = 3, delay = 1000)
    public MoveResponse execute() throws Exception {
        return pokemonApiClient.getMoves();
    }
}