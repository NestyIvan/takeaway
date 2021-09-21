package com.takeaway.testcases.api.themoviedb.list;

import com.takeaway.core.api.RestAssuredSettings;
import com.takeaway.core.api.themoviedb.EndPoints;
import com.takeaway.core.api.themoviedb.factories.MovieListFactory;
import com.takeaway.core.api.themoviedb.factories.SpecFactory;
import com.takeaway.core.api.themoviedb.helpers.MovieListHelper;
import com.takeaway.core.api.themoviedb.model.MovieList;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class UpdateListTests {

  private static int listId;

  @BeforeClass
  public static void setUp() {
    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    Response response = MovieListHelper.createPublicDefaultList();
    listId = response.body().jsonPath().getInt("id");
  }

  @AfterClass
  public static void cleanUp() {
    MovieListHelper.deleteList(listId);
  }

  @Test
  public void updateListAllFieldsTest() {
    MovieListHelper.updateList(MovieListFactory.getListWithAllFields(), listId);
  }

  @Test
  public void updatePrivateListTest() {
    Response response = MovieListHelper.createPrivateList();
    int listId = response.body().jsonPath().getInt("id");
    // try to update the list without access token
    given()
        .spec(RestAssuredSettings.requestSpecNoAuth)
        .put(EndPoints.UPDATE_LIST, listId)
        .then()
        .spec(SpecFactory.getSpecUnauthorized());
    // update with access token
    MovieListHelper.updateList(MovieListFactory.getListWithAllFields(), listId);
    MovieListHelper.deleteList(listId);
  }

  @Test
  public void makePublicListPrivateTest() {
    Response response = MovieListHelper.createPublicDefaultList();
    int listId = response.body().jsonPath().getInt("id");
    // make public list private
    MovieListHelper.updateList(MovieListFactory.getListWithAccess(false), listId);

    given()
        .spec(RestAssuredSettings.requestSpecNoAuth)
        .get(EndPoints.GET_LIST, listId, 1, RestAssuredSettings.API_KEY)
        .then()
        .spec(SpecFactory.getListIsPrivateSpec());
    MovieListHelper.deleteList(listId);
  }

  @Test
  public void makePrivateListPublicTest() {
    MovieList publicMovieList = MovieListFactory.getListWithAccess(true);
    Response response = MovieListHelper.createPrivateList();
    int listId = response.body().jsonPath().getInt("id");
    // make private list public
    MovieListHelper.updateList(publicMovieList, listId);

    given()
        .spec(RestAssuredSettings.requestSpecNoAuth)
        .get(EndPoints.GET_LIST, listId, 1, RestAssuredSettings.API_KEY)
        .then()
        .spec(SpecFactory.getListSpecSuccess(publicMovieList));
    MovieListHelper.deleteList(listId);
  }

  @Test
  public void updateNonExistingListTest() {
    given()
        .spec(RestAssuredSettings.requestSpecWithAuth)
        .body(MovieListFactory.getListWithAllFields())
        .when()
        .put(EndPoints.UPDATE_LIST, listId * 10)
        .then()
        .spec(SpecFactory.getSpecNotFound());
  }
}
