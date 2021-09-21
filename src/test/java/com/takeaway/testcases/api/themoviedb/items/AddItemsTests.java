package com.takeaway.testcases.api.themoviedb.items;

import com.takeaway.core.api.RestAssuredSettings;
import com.takeaway.core.api.themoviedb.EndPoints;
import com.takeaway.core.api.themoviedb.factories.ItemsFactory;
import com.takeaway.core.api.themoviedb.factories.Movie;
import com.takeaway.core.api.themoviedb.factories.MovieListResultFactory;
import com.takeaway.core.api.themoviedb.factories.SpecFactory;
import com.takeaway.core.api.themoviedb.helpers.ItemHelper;
import com.takeaway.core.api.themoviedb.helpers.MovieListHelper;
import com.takeaway.core.api.themoviedb.model.ItemList;
import com.takeaway.core.api.themoviedb.model.MovieList;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;

@Log4j
public class AddItemsTests {

  private Response defaultListResponse;

  @Before
  public void setUp() {
    defaultListResponse = MovieListHelper.createPublicDefaultList();
  }

  @After
  public void cleanUp() {
    MovieListHelper.deleteList(defaultListResponse.body().jsonPath().getInt("id"));
  }

  @Test
  public void addOneItemTest() {
    int listId = defaultListResponse.body().jsonPath().getInt("id");
    ItemList itemList = ItemsFactory.getListWithMediaId(Movie.FIGHT_CLUB);
    ItemHelper.addItems(listId, itemList);
    MovieList movieList = MovieListHelper.getPublicList(listId);
    assertThat(
        "The movie list should have a Fight Club in result",
        movieList.getResults().get(0).equals(MovieListResultFactory.getResultForFightClub()));
  }

  @Test
  public void addEmptyItemTest() {
    int listId = defaultListResponse.body().jsonPath().getInt("id");
    ItemList itemList = new ItemList(Collections.emptyList());
    ItemHelper.addItems(listId, itemList);
    MovieList movieList = MovieListHelper.getPublicList(listId);
    assertThat("The movie list should be empty", movieList.getResults().size() == 0);
  }

  @Test
  public void addSeveralItemsTest() {
    int listId = defaultListResponse.body().jsonPath().getInt("id");
    ItemList itemList = ItemsFactory.getAllMediaList();
    ItemHelper.addItems(listId, itemList);
    MovieList movieList = MovieListHelper.getPublicList(listId);
    assertThat(
        "The movie list size should be correct",
        movieList.getResults().size() == itemList.getItems().size());
  }

  @Test
  public void addDuplicateItemTest() {
    int listId = defaultListResponse.body().jsonPath().getInt("id");
    ItemList itemList = ItemsFactory.getListWithMediaId(Movie.FIGHT_CLUB);
    ItemHelper.addItems(listId, itemList);
    ItemHelper.addItems(listId, itemList);
    MovieList movieList = MovieListHelper.getPublicList(listId);
    assertThat("The movie list size should be correct", movieList.getResults().size() == 1);
  }

  @Test
  public void addItemNoAuthTest() {
    int listId = defaultListResponse.body().jsonPath().getInt("id");
    given()
        .spec(RestAssuredSettings.requestSpecNoAuth)
        .body(ItemsFactory.getListWithMediaId(Movie.FIGHT_CLUB))
        .when()
        .post(EndPoints.ADD_ITEMS, listId)
        .then()
        .spec(SpecFactory.getSpecUnauthorized());
  }
}
