package com.banco.austro.processor;

import com.banco.austro.service.ConcatenationService;
import com.banco.austro.service.ServiceResult;
import com.banco.austro.validation.ValidationStrategy;
import com.banco.austro.validation.NullBlankValidationStrategy;
import com.banco.austro.validation.SqlInjectionValidationStrategy;
import com.banco.austro.response.ResponseBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
public class ConcatenationRequestProcessor extends RequestProcessor {
    
    @Inject
    ConcatenationService concatenationService;
    
    @Inject
    NullBlankValidationStrategy nullBlankValidator;
    
    @Inject
    SqlInjectionValidationStrategy sqlInjectionValidator;
    
    @Override
    protected boolean preValidate(List<String> params) {
        return params != null && params.size() == 5;
    }
    
    @Override
    protected ServiceResult executeBusinessLogic(List<String> params) {
        List<ValidationStrategy> validators = List.of(nullBlankValidator, sqlInjectionValidator);
        return concatenationService.processAndConcatenate(params, validators);
    }
    
    @Override
    protected Response createSuccessResponse(String data) {
        return ResponseBuilder.create().success(data).build();
    }
    
    @Override
    protected Response createErrorResponse(String message) {
        return ResponseBuilder.create().badRequest(message).build();
    }
}