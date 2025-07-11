package com.banco.austro.response;

import jakarta.ws.rs.core.Response;

public class ResponseBuilder {
    private int status;
    private String entity;

    private ResponseBuilder() {}

    public static ResponseBuilder create() {
        return new ResponseBuilder();
    }

    public ResponseBuilder success(String data) {
        this.status = Response.Status.OK.getStatusCode();
        this.entity = data;
        return this;
    }

    public ResponseBuilder badRequest(String message) {
        this.status = Response.Status.BAD_REQUEST.getStatusCode();
        this.entity = message;
        return this;
    }

    public Response build() {
        return Response.status(status).entity(entity).build();
    }
}