package com.takeaway.core.api.themoviedb.helpers;

import com.takeaway.core.api.RestAssuredSettings;
import com.takeaway.core.api.themoviedb.EndPoints;
import com.takeaway.core.api.themoviedb.factories.SpecFactory;
import com.takeaway.core.api.themoviedb.model.ItemList;

import static io.restassured.RestAssured.given;

public class ItemHelper {

  public static void addItems(int listId, ItemList itemList) {
    given()
        .spec(RestAssuredSettings.requestSpecWithAuth)
        .body(itemList)
        .when()
        .post(EndPoints.ADD_ITEMS, listId)
        .then()
        .spec(SpecFactory.getAddItemsSpecSuccess(itemList));
  }

  public static void updateItems(int listId, ItemList itemList) {
    given()
        .spec(RestAssuredSettings.requestSpecWithAuth)
        .body(itemList)
        .when()
        .put(EndPoints.UPDATE_ITEMS, listId)
        .then()
        .spec(SpecFactory.getAddItemsSpecSuccess(itemList));
  }
}
