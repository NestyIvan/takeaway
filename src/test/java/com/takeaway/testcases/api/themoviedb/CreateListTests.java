package com.takeaway.testcases.api.themoviedb;

import com.takeaway.core.api.RestAssuredSettings;
import com.takeaway.core.api.themoviedb.EndPoints;
import com.takeaway.core.api.themoviedb.factories.MovieListFactory;
import com.takeaway.core.api.themoviedb.factories.SpecFactory;
import com.takeaway.core.api.themoviedb.helpers.MovieListHelper;
import io.restassured.response.Response;
import org.junit.AfterClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class CreateListTests {

  private static final List<Integer> listIds = new ArrayList<>();

  @AfterClass
  public static void cleanUp() {
    listIds.forEach(MovieListHelper::deleteList);
  }

  @Test
  public void createListWithRequiredFieldsTest() {
    Response response = MovieListHelper.createPublicDefaultList();
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
    Response response = MovieListHelper.createPrivateList();
    int listId = response.body().jsonPath().getInt("id");
    // try to get the list without access token
    given()
        .spec(RestAssuredSettings.requestSpecNoAuth)
        .get(EndPoints.GET_LIST, listId, 1, RestAssuredSettings.API_KEY)
        .then()
        .spec(SpecFactory.getListSpecUnauthorized());
    // get the list with access token
    given()
        .spec(RestAssuredSettings.getRequestSpecWithAuth())
        .get(EndPoints.GET_LIST, listId, 1, RestAssuredSettings.API_KEY)
        .then()
        .statusCode(200);
    listIds.add(listId);
  }
}
