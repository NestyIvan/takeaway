package com.takeaway.testcases.api.themoviedb.list;

import com.takeaway.core.api.RestAssuredSettings;
import com.takeaway.core.api.themoviedb.EndPoints;
import com.takeaway.core.api.themoviedb.factories.MovieListFactory;
import com.takeaway.core.api.themoviedb.factories.SpecFactory;
import com.takeaway.core.api.themoviedb.helpers.MovieListHelper;
import com.takeaway.testcases.api.themoviedb.BaseTests;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class CreateListTests extends BaseTests {

  @Test
  public void createListWithRequiredFieldsTest() {
    Response response = MovieListHelper.createPublicDefaultList();
    MovieListHelper.deleteList(response.body().jsonPath().getInt("id"));
  }

  @Test
  public void createListWithAllFieldsTest() {
    Response response =
        given()
            .spec(RestAssuredSettings.requestSpecWithAuth)
            .body(MovieListFactory.getListWithAllFields())
            .when()
            .post(EndPoints.CREATE_LIST, RestAssuredSettings.API_KEY)
            .then()
            .spec(SpecFactory.getCreateListSpec())
            .extract()
            .response();
    MovieListHelper.deleteList(response.body().jsonPath().getInt("id"));
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
        .spec(SpecFactory.getListIsPrivateSpec());
    // get the list with access token
    given()
        .spec(RestAssuredSettings.requestSpecWithAuth)
        .get(EndPoints.GET_LIST, listId, 1, RestAssuredSettings.API_KEY)
        .then()
        .statusCode(200);
    MovieListHelper.deleteList(listId);
  }

  @Test
  public void createListWithOptionalFieldsOnlyNegativeTest() {
    given()
        .spec(RestAssuredSettings.requestSpecWithAuth)
        .body(MovieListFactory.getListWithOptionalFieldsOnly())
        .when()
        .post(EndPoints.CREATE_LIST, RestAssuredSettings.API_KEY)
        .then()
        .statusCode(422);
  }

  @Test
  public void createListNoAuthTest() {
    given()
        .spec(RestAssuredSettings.requestSpecNoAuth)
        .body(MovieListFactory.getDefaultList())
        .when()
        .post(EndPoints.CREATE_LIST, RestAssuredSettings.API_KEY)
        .then()
        .spec(SpecFactory.getCreateListUnauthorizedSpec());
  }
}
