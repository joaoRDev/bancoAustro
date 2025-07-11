package com.banco.austro.resource;

import com.banco.austro.service.PokemonService;
import com.banco.austro.service.ServiceResult;
import com.banco.austro.dto.MoveResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;

/**
 * Resource REST para operaciones con Pokemon API
 * Consume API externa con tolerancia a fallos
 */
@Path("/api/v2/move")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Pokemon API", description = "Operaciones con API externa de Pokemon")
public class PokemonResource {

    private static final Logger LOG = Logger.getLogger(PokemonResource.class);

    @Inject
    PokemonService pokemonService;

    @GET
    @Operation(
        summary = "Obtener movimientos de Pokemon",
        description = "Consume Pokemon API con estrategias de tolerancia a fallos"
    )
    @APIResponse(
        responseCode = "200",
        description = "Movimientos obtenidos exitosamente",
        content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = MoveResponse.class))
    )
    @APIResponse(
        responseCode = "503",
        description = "Servicio no disponible",
        content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response getMoves(
            @Parameter(description = "Estrategia de tolerancia a fallos (retry, circuitbreaker)")
            @QueryParam("strategy") @DefaultValue("retry") String strategy,
            @Context HttpHeaders headers) {

        headers.getRequestHeaders().forEach((key, value) -> 
            LOG.infof("Header propagated: %s = %s", key, value));
        
        ServiceResult<MoveResponse> result = pokemonService.getMoves(strategy);
        
        if (result.isSuccess()) {
            return Response.ok(result.getData()).build();
        } else {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity("{\"error\":\"" + result.getErrorMessage() + "\"}")
                    .build();
        }
    }
}