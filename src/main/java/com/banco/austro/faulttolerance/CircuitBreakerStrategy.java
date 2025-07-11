package com.banco.austro.faulttolerance;

import com.banco.austro.client.PokemonApiClient;
import com.banco.austro.dto.MoveResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class CircuitBreakerStrategy implements FaultToleranceStrategy {
    
    @Inject
    @RestClient
    PokemonApiClient pokemonApiClient;
    
    @Override
    @CircuitBreaker(requestVolumeThreshold = 4, failureRatio = 0.5, delay = 5000)
    @Fallback(fallbackMethod = "fallbackResponse")
    public MoveResponse execute() throws Exception {
        return pokemonApiClient.getMoves();
    }
    
    public MoveResponse fallbackResponse() {
        MoveResponse fallback = new MoveResponse();
        fallback.setCount(0);
        fallback.setNext(null);
        fallback.setPrevious(null);
        fallback.setResults(java.util.Collections.emptyList());
        return fallback;
    }
}