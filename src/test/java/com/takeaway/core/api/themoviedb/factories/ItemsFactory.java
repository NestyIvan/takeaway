package com.takeaway.core.api.themoviedb.factories;

import com.takeaway.core.api.themoviedb.model.Item;
import com.takeaway.core.api.themoviedb.model.ItemList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemsFactory {

  public static ItemList getAllMediaList() {
    List<Item> itemList = new ArrayList<>();
    Movie.getAllMovies().forEach(it -> itemList.add(createItem(it)));
    return new ItemList(itemList);
  }

  public static ItemList getListWithMediaId(Movie media) {
    return new ItemList(Collections.singletonList(createItem(media)));
  }

  public static Item createItem(Movie media) {
    return new Item(media.getMediaType(), media.getMediaId());
  }
}
