package com.takeaway.testcases.api.themoviedb;

import com.takeaway.core.api.RestAssuredSettings;
import com.takeaway.core.api.themoviedb.EndPoints;
import com.takeaway.core.api.themoviedb.factories.ItemsFactory;
import com.takeaway.core.api.themoviedb.factories.Movie;
import com.takeaway.core.api.themoviedb.factories.MovieListResultFactory;
import com.takeaway.core.api.themoviedb.factories.SpecFactory;
import com.takeaway.core.api.themoviedb.helpers.MovieListHelper;
import com.takeaway.core.api.themoviedb.model.ItemList;
import com.takeaway.core.api.themoviedb.model.MovieList;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;

@Log4j
public class AddItemsTests {
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
  public void addOneItemTest() {
    ItemList itemList = ItemsFactory.getListWithMediaId(Movie.FIGHT_CLUB);
    given()
        .spec(RestAssuredSettings.requestSpecWithAuth)
        .body(itemList)
        .when()
        .post(EndPoints.ADD_ITEMS, listId)
        .then()
        .spec(SpecFactory.getAddItemsSpecSuccess(itemList));
    // get the list
    MovieList movieList =
        given()
            .spec(RestAssuredSettings.requestSpecNoAuth)
            .get(EndPoints.GET_LIST, listId, 1, RestAssuredSettings.API_KEY)
            .then()
            .extract()
            .body()
            .as(MovieList.class);
    assertThat(
        "The movie list should have a Fight Club in result",
        movieList.getResults().get(0).equals(MovieListResultFactory.getResultForFightClub()));
  }
}
