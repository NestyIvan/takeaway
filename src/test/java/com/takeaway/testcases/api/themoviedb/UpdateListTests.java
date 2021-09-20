package com.takeaway.testcases.api.themoviedb;

import com.takeaway.core.api.RestAssuredSettings;
import com.takeaway.core.api.themoviedb.EndPoints;
import com.takeaway.core.api.themoviedb.MovieListFactory;
import com.takeaway.core.api.themoviedb.SpecFactory;
import com.takeaway.core.api.themoviedb.helpers.MovieListHelper;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class UpdateListTests {

  private static int listId;

  @BeforeClass
  public static void setUp() {
    Response response = MovieListHelper.createPublicDefaultList();
    listId = response.body().jsonPath().getInt("id");
  }

  @AfterClass
  public static void cleanUp() {
    MovieListHelper.deleteList(listId);
  }

  @Test
  public void updateListAllFieldsTest() {
    given()
        .spec(RestAssuredSettings.getRequestSpecWithAuth())
        .body(MovieListFactory.getListWithAllFields().toString())
        .when()
        .put(EndPoints.UPDATE_LIST, listId)
        .then()
        .spec(SpecFactory.getUpdateListSpec());
  }

  @Test
  public void updatePrivateListTest() {
    Response response = MovieListHelper.createPrivateList();
    int listId = response.body().jsonPath().getInt("id");
    // try to update the list without access token
    given()
        .put(EndPoints.UPDATE_LIST, listId)
        .then()
        .spec(SpecFactory.getUpdateListSpecUnauthorized());
    // update with access token
    given()
        .spec(RestAssuredSettings.getRequestSpecWithAuth())
        .body(MovieListFactory.getListWithAllFields().toString())
        .when()
        .put(EndPoints.UPDATE_LIST, listId)
        .then()
        .spec(SpecFactory.getUpdateListSpec());
    MovieListHelper.deleteList(listId);
  }

  @Test
  public void makePublicListPrivateTest() {
    Response response = MovieListHelper.createPublicDefaultList();
    int listId = response.body().jsonPath().getInt("id");
    // make public list private
    given()
        .spec(RestAssuredSettings.getRequestSpecWithAuth())
        .body(MovieListFactory.getPrivateList().toString())
        .when()
        .put(EndPoints.UPDATE_LIST, listId)
        .then()
        .spec(SpecFactory.getUpdateListSpec());

    given()
        .get(EndPoints.GET_LIST, listId, 1, RestAssuredSettings.API_KEY)
        .then()
        .spec(SpecFactory.getListSpecUnauthorized());
    MovieListHelper.deleteList(listId);
  }

  @Test
  public void makePrivateListPublicTest() {
    JSONObject publicMovieList = MovieListFactory.getDefaultList();
    Response response = MovieListHelper.createPrivateList();
    int listId = response.body().jsonPath().getInt("id");
    // make private list public
    given()
        .spec(RestAssuredSettings.getRequestSpecWithAuth())
        .body(publicMovieList.toString())
        .when()
        .put(EndPoints.UPDATE_LIST, listId)
        .then()
        .spec(SpecFactory.getUpdateListSpec());

    given()
        .get(EndPoints.GET_LIST, listId, 1, RestAssuredSettings.API_KEY)
        .then()
        .spec(SpecFactory.getListSpecSuccess(publicMovieList));
    MovieListHelper.deleteList(listId);
  }

  @Test
  public void updateNonExistingListTest() {
    given()
        .spec(RestAssuredSettings.getRequestSpecWithAuth())
        .body(MovieListFactory.getListWithAllFields().toString())
        .when()
        .put(EndPoints.UPDATE_LIST, listId * 10)
        .then()
        .spec(SpecFactory.getUpdateListSpecNotFound());
  }
}
