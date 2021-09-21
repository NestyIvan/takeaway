package com.takeaway.testcases.api.themoviedb.list;

import com.takeaway.core.api.themoviedb.factories.ItemsFactory;
import com.takeaway.core.api.themoviedb.helpers.ItemHelper;
import com.takeaway.core.api.themoviedb.helpers.MovieListHelper;
import com.takeaway.core.api.themoviedb.model.MovieList;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class ClearListTests {

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
  public void clearAllItemsTest() {
    int listId = defaultListResponse.body().jsonPath().getInt("id");
    ItemHelper.addItems(listId, ItemsFactory.getAllMediaList());
    MovieListHelper.clearList(listId);

    MovieList movieList = MovieListHelper.getPublicList(listId);
    assertThat("The movie list should be empty", movieList.getResults().size() == 0);
  }

  @Test
  public void clearEmptyListTest() {
    int listId = defaultListResponse.body().jsonPath().getInt("id");
    MovieListHelper.clearList(listId);
  }
}
