package com.banco.austro.resource;

import com.banco.austro.processor.ConcatenationRequestProcessor;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import java.util.List;

/**
 * Resource REST para operaciones de concatenación
 * Expone endpoint para concatenar 5 parámetros de texto
 */
@Path("/api/v1/test")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Tag(name = "Concatenación", description = "Operaciones de concatenación de parámetros")
public class TestResource {

    @Inject
    ConcatenationRequestProcessor processor;

    @POST
    @Operation(
        summary = "Concatenar parámetros",
        description = "Concatena 5 parámetros de texto aplicando validaciones de seguridad"
    )
    @APIResponse(
        responseCode = "200",
        description = "Concatenación exitosa",
        content = @Content(mediaType = MediaType.TEXT_PLAIN)
    )
    @APIResponse(
        responseCode = "400",
        description = "Error de validación",
        content = @Content(mediaType = MediaType.TEXT_PLAIN)
    )
    public Response concatenateParams(
            @Parameter(description = "Primer parámetro", required = true)
            @FormParam("param1") String param1,
            @Parameter(description = "Segundo parámetro", required = true)
            @FormParam("param2") String param2,
            @Parameter(description = "Tercer parámetro", required = true)
            @FormParam("param3") String param3,
            @Parameter(description = "Cuarto parámetro", required = true)
            @FormParam("param4") String param4,
            @Parameter(description = "Quinto parámetro", required = true)
            @FormParam("param5") String param5) {
        
        List<String> params = List.of(param1, param2, param3, param4, param5);
        return processor.processRequest(params);
    }
}