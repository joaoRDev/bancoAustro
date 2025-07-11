package com.banco.austro.resource;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
public class PokemonResourceTest {

    @Test
    public void testGetMovesWithRetryStrategy() {
        given()
            .queryParam("strategy", "retry")
            .when().get("/api/v2/move")
            .then()
            .statusCode(200)
            .body("count", notNullValue())
            .body("results", notNullValue());
    }

    @Test
    public void testGetMovesWithCircuitBreakerStrategy() {
        given()
            .queryParam("strategy", "circuitbreaker")
            .when().get("/api/v2/move")
            .then()
            .statusCode(200)
            .body("count", notNullValue());
    }

    @Test
    public void testGetMovesDefaultStrategy() {
        given()
            .when().get("/api/v2/move")
            .then()
            .statusCode(200)
            .body("count", notNullValue());
    }
}