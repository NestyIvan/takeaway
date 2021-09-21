package com.takeaway.core.api.themoviedb.factories;

import com.github.javafaker.Faker;
import com.takeaway.core.api.themoviedb.model.Item;
import com.takeaway.core.api.themoviedb.model.ItemList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemsFactory {
  private static final Faker faker = new Faker();

  public static ItemList getAllMediaList() {
    List<Item> itemList = new ArrayList<>();
    Movie.getAllMovies().forEach(it -> itemList.add(new Item(it.getMediaType(), it.getMediaId())));
    return new ItemList(itemList);
  }

  public static ItemList getListWithMediaId(Movie media) {
    return new ItemList(
        Collections.singletonList(new Item(media.getMediaType(), media.getMediaId())));
  }
}
