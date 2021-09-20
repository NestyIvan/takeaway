package com.takeaway.testcases.api.themoviedb;

import com.takeaway.core.api.RestAssuredSettings;
import com.takeaway.core.api.themoviedb.EndPoints;
import com.takeaway.core.api.themoviedb.MovieListFactory;
import com.takeaway.core.api.themoviedb.SpecFactory;
import io.restassured.response.Response;
import org.junit.AfterClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class CreateListTests extends BaseTest {

  private static final List<Integer> listIds = new ArrayList<>();

  @AfterClass
  public static void cleanUp() {
    listIds.forEach(
        listId ->
            given()
                .spec(RestAssuredSettings.getRequestSpecWithAuth())
                .expect()
                .response()
                .statusCode(200)
                .when()
                .delete(EndPoints.DELETE_LIST, listId, RestAssuredSettings.API_KEY));
  }

  @Test
  public void createListWithRequiredFieldsTest() {
    Response response =
        given()
            .spec(RestAssuredSettings.getRequestSpecWithAuth())
            .body(MovieListFactory.getDefaultList().toString())
            .when()
            .post(EndPoints.CREATE_LIST, RestAssuredSettings.API_KEY)
            .then()
            .spec(SpecFactory.getCreateListSpec())
            .extract()
            .response();
    listIds.add(response.body().jsonPath().getInt("id"));
  }

  @Test
  public void createListWithAllFieldsTest() {
    Response response =
        given()
            .spec(RestAssuredSettings.getRequestSpecWithAuth())
            .body(MovieListFactory.getListWithAllFields().toString())
            .when()
            .post(EndPoints.CREATE_LIST, RestAssuredSettings.API_KEY)
            .then()
            .spec(SpecFactory.getCreateListSpec())
            .extract()
            .response();
    listIds.add(response.body().jsonPath().getInt("id"));
  }

  @Test
  public void createPrivateListTest() {
    Response response =
        given()
            .spec(RestAssuredSettings.getRequestSpecWithAuth())
            .body(MovieListFactory.getPrivateList().toString())
            .when()
            .post(EndPoints.CREATE_LIST, RestAssuredSettings.API_KEY)
            .then()
            .spec(SpecFactory.getCreateListSpec())
            .extract()
            .response();
    int listId = response.body().jsonPath().getInt("id");
    // try to get the list without access token
    given().get(EndPoints.GET_LIST, listId, 1, RestAssuredSettings.API_KEY).then().statusCode(401);
    // get the list with access token
    given()
        .spec(RestAssuredSettings.getRequestSpecWithAuth())
        .get(EndPoints.GET_LIST, listId, 1, RestAssuredSettings.API_KEY)
        .then()
        .statusCode(200);
    listIds.add(listId);
  }
}
