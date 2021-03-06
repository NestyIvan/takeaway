package com.takeaway.testcases.api.themoviedb.items;

import com.takeaway.core.api.RestAssuredSettings;
import com.takeaway.core.api.themoviedb.EndPoints;
import com.takeaway.core.api.themoviedb.factories.ItemsFactory;
import com.takeaway.core.api.themoviedb.factories.Movie;
import com.takeaway.core.api.themoviedb.factories.SpecFactory;
import com.takeaway.core.api.themoviedb.helpers.ItemHelper;
import com.takeaway.core.api.themoviedb.helpers.MovieListHelper;
import com.takeaway.core.api.themoviedb.model.Item;
import com.takeaway.core.api.themoviedb.model.ItemList;
import com.takeaway.core.api.themoviedb.model.MovieList;
import com.takeaway.testcases.api.themoviedb.BaseTests;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;

public class DeleteItemsTests extends BaseTests {

  private static final ItemList allMediaList = ItemsFactory.getAllMediaList();
  private Response defaultListResponse;

  @Before
  public void setUpEach() {
    defaultListResponse = MovieListHelper.createPublicDefaultList();
  }

  @After
  public void cleanUpEach() {
    MovieListHelper.deleteList(defaultListResponse.body().jsonPath().getInt("id"));
  }

  @Test
  public void deleteOneItemTest() {
    int listId = defaultListResponse.body().jsonPath().getInt("id");
    ItemHelper.addItems(listId, allMediaList);

    ItemList itemList = ItemsFactory.getListWithMediaId(Movie.FIGHT_CLUB);
    ItemHelper.deleteItems(listId, itemList);
    // check that the number of results is correct after delete
    MovieList movieList = MovieListHelper.getPublicList(listId);
    assertThat(
        "The movie list size should be correct",
        movieList.getResults().size() == allMediaList.getItems().size() - 1);
  }

  @Test
  public void deleteAllItemsTest() {
    int listId = defaultListResponse.body().jsonPath().getInt("id");
    ItemHelper.addItems(listId, allMediaList);

    ItemHelper.deleteItems(listId, allMediaList);
    // check that the number of results is correct after delete
    MovieList movieList = MovieListHelper.getPublicList(listId);
    assertThat("The movie list size should be correct", movieList.getResults().size() == 0);
  }

  @Test
  public void deleteNonExistingItemsTest() {
    int listId = defaultListResponse.body().jsonPath().getInt("id");
    List<Item> items = new ArrayList<>();
    items.add(ItemsFactory.createItem(Movie.FIGHT_CLUB));
    ItemList itemList = new ItemList(items);
    ItemHelper.addItems(listId, itemList);
    // put one more item that is not in the list and delete 2 instead of 1
    itemList.getItems().add(ItemsFactory.createItem(Movie.TENET));
    given()
        .spec(RestAssuredSettings.requestSpecWithAuth)
        .body(itemList)
        .when()
        .delete(EndPoints.DELETE_ITEMS, listId)
        .then()
        .spec(
            SpecFactory.getUpdateItemsSpecFail(
                Collections.singletonList(Movie.TENET.getMediaId())));
    // check that the number of results is correct after delete
    MovieList movieList = MovieListHelper.getPublicList(listId);
    assertThat("The movie list size should be correct", movieList.getResults().size() == 0);
  }

  @Test
  public void deleteItemsNoAuthTest() {
    int listId = defaultListResponse.body().jsonPath().getInt("id");
    given()
        .spec(RestAssuredSettings.requestSpecNoAuth)
        .body(new ItemList(Collections.emptyList()))
        .when()
        .delete(EndPoints.DELETE_ITEMS, listId)
        .then()
        .spec(SpecFactory.getSpecUnauthorized());
  }
}
