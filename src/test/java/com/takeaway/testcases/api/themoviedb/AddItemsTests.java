package com.takeaway.testcases.api.themoviedb;

import com.takeaway.core.api.themoviedb.factories.ItemsFactory;
import com.takeaway.core.api.themoviedb.factories.Movie;
import com.takeaway.core.api.themoviedb.factories.MovieListResultFactory;
import com.takeaway.core.api.themoviedb.helpers.MovieListHelper;
import com.takeaway.core.api.themoviedb.model.ItemList;
import com.takeaway.core.api.themoviedb.model.MovieList;
import lombok.extern.log4j.Log4j;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;

@Log4j
public class AddItemsTests {

  @Test
  public void addOneItemTest() {
    int listId = MovieListHelper.createPublicDefaultList().body().jsonPath().getInt("id");
    ItemList itemList = ItemsFactory.getListWithMediaId(Movie.FIGHT_CLUB);
    MovieListHelper.addItems(listId, itemList);
    MovieList movieList = MovieListHelper.getPublicList(listId);
    assertThat(
        "The movie list should have a Fight Club in result",
        movieList.getResults().get(0).equals(MovieListResultFactory.getResultForFightClub()));
    MovieListHelper.deleteList(listId);
  }

  @Test
  public void addEmptyItemTest() {
    int listId = MovieListHelper.createPublicDefaultList().body().jsonPath().getInt("id");
    ItemList itemList = new ItemList(Collections.emptyList());
    MovieListHelper.addItems(listId, itemList);
    MovieList movieList = MovieListHelper.getPublicList(listId);
    assertThat("The movie list should be empty", movieList.getResults().size() == 0);
    MovieListHelper.deleteList(listId);
  }

  @Test
  public void addSeveralItemsTest() {
    int listId = MovieListHelper.createPublicDefaultList().body().jsonPath().getInt("id");
    ItemList itemList = ItemsFactory.getAllMediaList();
    MovieListHelper.addItems(listId, itemList);
    MovieList movieList = MovieListHelper.getPublicList(listId);
    assertThat(
        "The movie list size should be correct",
        movieList.getResults().size() == itemList.getItems().size());
    MovieListHelper.deleteList(listId);
  }

  @Test
  public void addDuplicateItemTest() {
    int listId = MovieListHelper.createPublicDefaultList().body().jsonPath().getInt("id");
    ItemList itemList = ItemsFactory.getListWithMediaId(Movie.FIGHT_CLUB);
    MovieListHelper.addItems(listId, itemList);
    MovieListHelper.addItems(listId, itemList);
    MovieList movieList = MovieListHelper.getPublicList(listId);
    assertThat("The movie list size should be correct", movieList.getResults().size() == 1);
    MovieListHelper.deleteList(listId);
  }
}
