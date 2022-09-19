package org.gs;

import io.quarkus.test.junit.QuarkusTest;
import javax.json.Json;
import javax.json.JsonObject;
import org.junit.jupiter.api.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.testcontainers.shaded.org.hamcrest.CoreMatchers.hasItems;

@QuarkusTest
@Tag("integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MovieResourceTest {

//    @Test
//    @Order(1)
//    public void testHelloEndpoint() {
//        given()
//          .when().get("/movies")
//          .then()
//             .statusCode(200)
//             .body(is("Hello RESTEasy"));
//    }

    @Test
    @Order(1)
    void getAll() {
        given()
                .when().get("/movies")
                .then()
                .body("size()", equalTo(1))
                .body("id", hasItem(1))
                .body("title", hasItem("First"))
                .body("description", hasItem("FirstMovie"))
                .body("director", hasItem("Artur"))
                .body("country", hasItem("ARM"))
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @Order(1)
    void getById() {
        given()
                .when().get("/movies/1")
                .then()
                .body("id", equalTo(1))
                .body("title", equalTo("First"))
                .body("description", equalTo("FirstMovie"))
                .body("director", equalTo("Artur"))
                .body("country", equalTo("ARM"))
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @Order(1)
    void getByTitle() {
        given()
                .when()
                .get("/movies/title/First")
                .then()
                .body("id", equalTo(1))
                .body("title", equalTo("First"))
                .body("description", equalTo("FirstMovie"))
                .body("director", equalTo("Artur"))
                .body("country", equalTo("ARM"))
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @Order(2)
    void getByCountry() {
        given()
                .when()
                .get("/movies/country/ARM")
                .then()
                .body("size()", equalTo(1))
                .body("id", hasItem(1))
                .body("title", equalTo("First"))
                .body("description", equalTo("FirstMovie"))
                .body("director", equalTo("Artur"))
                .body("country", equalTo("ARM"))
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @Order(2)
    void create() {
        JsonObject jsonObject =
                (JsonObject) Json.createObjectBuilder()
                        .add("title", "Second")
                        .add("description", "SecondMovie")
                        .add("director", "Me")
                        .add("country", "ARM")
                        .build();

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonObject.toString())
                .when()
                .post("/movies")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode());
    }

    @Test
    @Order(3)
    void updateById(){

    }
    @Test
    @Order(4)
    void deleteById() {
        given()
                .when()
                .delete("/movies/1")
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());

        given().when().get("/movies/1").then().statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }
}