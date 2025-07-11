package com.banco.austro.faulttolerance;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class FaultToleranceFactory {
    
    @Inject
    RetryStrategy retryStrategy;
    
    @Inject
    CircuitBreakerStrategy circuitBreakerStrategy;
    
    public FaultToleranceStrategy getRetryStrategy() {
        return retryStrategy;
    }
    
    public FaultToleranceStrategy getCircuitBreakerStrategy() {
        return circuitBreakerStrategy;
    }
}