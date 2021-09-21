package com.takeaway.core.api.themoviedb.helpers;

import com.takeaway.core.api.RestAssuredSettings;
import com.takeaway.core.api.themoviedb.EndPoints;
import com.takeaway.core.api.themoviedb.factories.SpecFactory;
import com.takeaway.core.api.themoviedb.model.ItemList;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ItemHelper {

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
