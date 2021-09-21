package com.takeaway.core.api.themoviedb.helpers;

import com.takeaway.core.api.RestAssuredSettings;
import com.takeaway.core.api.themoviedb.EndPoints;
import com.takeaway.core.api.themoviedb.factories.MovieListFactory;
import com.takeaway.core.api.themoviedb.factories.SpecFactory;
import com.takeaway.core.api.themoviedb.model.ItemList;
import com.takeaway.core.api.themoviedb.model.MovieList;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class MovieListHelper {

  public static Response createList(String body) {
    return given()
        .spec(RestAssuredSettings.requestSpecWithAuth)
        .body(body)
        .when()
        .post(EndPoints.CREATE_LIST, RestAssuredSettings.API_KEY)
        .then()
        .spec(SpecFactory.getCreateListSpec())
        .extract()
        .response();
  }

  public static Response createPrivateList() {
    return createList(MovieListFactory.getListWithAccess(false).toString());
  }

  public static Response createPublicDefaultList() {
    return createList(MovieListFactory.getDefaultList().toString());
  }

  public static void deleteList(int listId) {
    given()
        .spec(RestAssuredSettings.requestSpecWithAuth)
        .expect()
        .response()
        .statusCode(200)
        .when()
        .delete(EndPoints.DELETE_LIST, listId, RestAssuredSettings.API_KEY);
  }

  public static void clearList(int listId) {
    given()
        .spec(RestAssuredSettings.requestSpecWithAuth)
        .when()
        .get(EndPoints.CLEAR_LIST, listId)
        .then()
        .spec(SpecFactory.getClearListSpecSuccess(listId));
  }

  public static MovieList getPublicList(int listId) {
    return given()
        .spec(RestAssuredSettings.requestSpecNoAuth)
        .get(EndPoints.GET_LIST, listId, 1, RestAssuredSettings.API_KEY)
        .then()
        .extract()
        .body()
        .as(MovieList.class);
  }

  public static Response addItems(int listId, ItemList itemList) {
    return given()
        .spec(RestAssuredSettings.requestSpecWithAuth)
        .body(itemList)
        .when()
        .post(EndPoints.ADD_ITEMS, listId)
        .then()
        .spec(SpecFactory.getAddItemsSpecSuccess(itemList))
        .extract()
        .response();
  }
}
