package com.banco.austro.client;

import com.banco.austro.dto.MoveResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "pokemon-api")
@Path("/api/v2")
public interface PokemonApiClient {
    
    @GET
    @Path("/move")
    @Produces(MediaType.APPLICATION_JSON)
    MoveResponse getMoves();
}