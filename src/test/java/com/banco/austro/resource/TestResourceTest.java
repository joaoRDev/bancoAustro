package com.banco.austro.resource;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
public class TestResourceTest {

    @Test
    public void testConcatenateParams() {
        given()
            .formParam("param1", "Hola")
            .formParam("param2", "Mundo")
            .formParam("param3", "Banco")
            .formParam("param4", "Del")
            .formParam("param5", "Austro")
            .when().post("/api/v1/test")
            .then()
            .statusCode(200)
            .body(is("HolaMundoBancoDelAustro"));
    }

    @Test
    public void testMissingParams() {
        given()
            .formParam("param1", "Hola")
            .formParam("param2", "")
            .formParam("param3", "Banco")
            .formParam("param4", "Del")
            .formParam("param5", "Austro")
            .when().post("/api/v1/test")
            .then()
            .statusCode(400)
            .body(containsString("nulo o vacío"));
    }

    @Test
    public void testSqlInjectionPrevention() {
        given()
            .formParam("param1", "SELECT * FROM users")
            .formParam("param2", "normal")
            .formParam("param3", "text")
            .formParam("param4", "here")
            .formParam("param5", "end")
            .when().post("/api/v1/test")
            .then()
            .statusCode(400)
            .body(containsString("no permitidos"));
    }
}