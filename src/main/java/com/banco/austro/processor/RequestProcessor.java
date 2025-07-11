package com.banco.austro.processor;

import com.banco.austro.service.ServiceResult;
import jakarta.ws.rs.core.Response;
import java.util.List;

public abstract class RequestProcessor {
    
    public final Response processRequest(List<String> params) {
        if (!preValidate(params)) {
            return createErrorResponse("Parámetros inválidos");
        }
        
        ServiceResult<?> result = executeBusinessLogic(params);
        
        if (result.isSuccess()) {
            return createSuccessResponse(result.getData().toString());
        } else {
            return createErrorResponse(result.getErrorMessage());
        }
    }
    
    protected abstract boolean preValidate(List<String> params);
    protected abstract ServiceResult<?> executeBusinessLogic(List<String> params);
    protected abstract Response createSuccessResponse(String data);
    protected abstract Response createErrorResponse(String message);
}