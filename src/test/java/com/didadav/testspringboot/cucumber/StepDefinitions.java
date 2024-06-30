package com.didadav.testspringboot.cucumber;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.didadav.testspringboot.entity.Film;
import com.didadav.testspringboot.service.FilmService;
import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonArray;
import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonObject;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class StepDefinitions extends SpringIntegrationTest {
    private static Response response;

    @Autowired
    FilmService filmService;
    @Autowired
    private ServletWebServerApplicationContext webServerAppCtxt;

    @Before
    public void setUp() throws Exception {
        RestAssured.baseURI = "http://localhost";
    }

    @When("the client calls api with id {long}")
    public void clientCallsGetApi(Long id) {
        RequestSpecification request = RestAssured.given().port(webServerAppCtxt.getWebServer().getPort());
        response = request.get("/api/film/" + id);
    }

    @When("the client calls api with following data")
    public void clientCallsPostApi(DataTable table) {
        List<List<String>> rows = table.asLists(String.class);
        RequestSpecification request = RestAssured.given()
                .port(webServerAppCtxt.getWebServer().getPort())
                .contentType(ContentType.JSON);
        JsonObject jsonObject = new JsonObject();
        for (List<String> columns : rows) {
            String[] acteurs = columns.get(2).split(",");
            JsonArray acteurArray = new JsonArray();
            Arrays.stream(acteurs).toList().forEach(acteur -> {
                String[] acteurName = acteur.split(" ");
                JsonObject acteurObject = new JsonObject()
                        .add("nom", acteurName[1])
                        .add("prenom", acteurName[0]);
                acteurArray.add(acteurObject);
            });
            jsonObject.add("titre", columns.get(0))
                    .add("description", columns.get(1))
                    .add("acteurs", acteurArray);
        }
        response = request.body(jsonObject.toString()).post("/api/film");
    }

    @Then("the client receives status code {int}")
    public void clientReceivesStatusCode(int expectedStatusCode) {
        response.then().assertThat().statusCode(expectedStatusCode);
    }

    @Then("the client receives film with following data")
    public void clientReceivesData(DataTable table) {
        List<List<String>> rows = table.asLists(String.class);
        for (List<String> columns : rows) {
            Film f = response.then().assertThat()
                    .body("titre", is(columns.get(1)))
                    .body("description", is(columns.get(2)))
                    .extract().as(Film.class);
            assertEquals(f.getActeurs().size(), 2);
        }
    }

    @Then("a film is added with following data")
    public void filmCreatedWithData(DataTable table) {
        List<List<String>> rows = table.asLists(String.class);
        for (List<String> columns : rows) {
            Film f = response.then().assertThat()
                    .body("titre", is(columns.get(1)))
                    .body("description", is(columns.get(2)))
                    .extract().as(Film.class);
            assertEquals(f.getActeurs().size(), 1);
        }
    }
}
