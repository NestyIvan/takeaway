package com.takeaway.testcases.api.themoviedb.items;

import com.takeaway.core.api.RestAssuredSettings;
import com.takeaway.core.api.themoviedb.EndPoints;
import com.takeaway.core.api.themoviedb.factories.ItemsFactory;
import com.takeaway.core.api.themoviedb.factories.SpecFactory;
import com.takeaway.core.api.themoviedb.helpers.ItemHelper;
import com.takeaway.core.api.themoviedb.helpers.MovieListHelper;
import com.takeaway.core.api.themoviedb.model.ItemList;
import com.takeaway.testcases.api.themoviedb.BaseTests;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static io.restassured.RestAssured.given;

public class UpdateItemsTests extends BaseTests {

  private static final ItemList allMediaList = ItemsFactory.getAllMediaList();
  private Response defaultListResponse;

  @Before
  public void setUpEach() {
    defaultListResponse = MovieListHelper.createPublicDefaultList();
    ItemHelper.addItems(defaultListResponse.body().jsonPath().getInt("id"), allMediaList);
  }

  @After
  public void cleanUpEach() {
    MovieListHelper.deleteList(defaultListResponse.body().jsonPath().getInt("id"));
  }

  @Test
  public void updateMultipleItemsTest() {
    int listId = defaultListResponse.body().jsonPath().getInt("id");
    ItemHelper.updateItems(listId, allMediaList);
  }

  @Test
  public void updateEmptyItemTest() {
    int listId = defaultListResponse.body().jsonPath().getInt("id");
    ItemHelper.updateItems(listId, new ItemList(Collections.emptyList()));
  }

  @Test
  public void updateItemNoAuthTest() {
    int listId = defaultListResponse.body().jsonPath().getInt("id");
    given()
        .spec(RestAssuredSettings.requestSpecNoAuth)
        .body(new ItemList(Collections.emptyList()))
        .when()
        .put(EndPoints.UPDATE_ITEMS, listId)
        .then()
        .spec(SpecFactory.getSpecUnauthorized());
  }
}
