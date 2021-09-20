package com.takeaway.core.api.themoviedb.helpers;

import com.takeaway.core.api.RestAssuredSettings;
import com.takeaway.core.api.themoviedb.EndPoints;
import com.takeaway.core.api.themoviedb.factories.MovieListFactory;
import com.takeaway.core.api.themoviedb.factories.SpecFactory;
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
}
