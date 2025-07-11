package com.banco.austro.scheduler;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class PokemonSchedulerTest {

    @Inject
    PokemonScheduler pokemonScheduler;

    @Test
    public void testSchedulerInjection() {
        assertNotNull(pokemonScheduler);
    }

    @Test
    public void testScheduledMethod() {
        // Test manual execution of scheduled method
        pokemonScheduler.scheduledPokemonCall();
    }
}