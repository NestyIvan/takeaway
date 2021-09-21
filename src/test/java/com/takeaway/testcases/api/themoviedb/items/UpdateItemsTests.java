package com.takeaway.testcases.api.themoviedb.items;

import com.takeaway.core.api.RestAssuredSettings;
import com.takeaway.core.api.themoviedb.EndPoints;
import com.takeaway.core.api.themoviedb.factories.ItemsFactory;
import com.takeaway.core.api.themoviedb.factories.SpecFactory;
import com.takeaway.core.api.themoviedb.helpers.ItemHelper;
import com.takeaway.core.api.themoviedb.helpers.MovieListHelper;
import com.takeaway.core.api.themoviedb.model.ItemList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collections;

import static io.restassured.RestAssured.given;

public class UpdateItemsTests {

  private static int listId;
  private static final ItemList allMediaList = ItemsFactory.getAllMediaList();

  @BeforeClass
  public static void setUp() {
    listId = MovieListHelper.createPublicDefaultList().body().jsonPath().getInt("id");
    ItemHelper.addItems(listId, allMediaList);
  }

  @AfterClass
  public static void cleanUp() {
    MovieListHelper.deleteList(listId);
  }

  @Test
  public void updateMultipleItemsTest() {
    ItemHelper.updateItems(listId, allMediaList);
  }

  @Test
  public void updateEmptyItemTest() {
    ItemHelper.updateItems(listId, new ItemList(Collections.emptyList()));
  }

  @Test
  public void updateItemNoAuthTest() {
    given()
        .spec(RestAssuredSettings.requestSpecNoAuth)
        .body(new ItemList(Collections.emptyList()))
        .when()
        .put(EndPoints.UPDATE_ITEMS, listId)
        .then()
        .spec(SpecFactory.getSpecUnauthorized());
  }
}
