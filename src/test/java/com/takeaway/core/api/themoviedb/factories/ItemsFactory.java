package com.takeaway.core.api.themoviedb.factories;

import com.github.javafaker.Faker;
import com.takeaway.core.api.themoviedb.model.Item;
import com.takeaway.core.api.themoviedb.model.ItemList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemsFactory {
  private static final Faker faker = new Faker();

  public static ItemList getItemList(int numberOfItems) {
    List<Item> itemList = new ArrayList<>();
    for (int i = 0; i < numberOfItems; i++) {
      itemList.add(new Item(faker.regexify("[a-z1-9]{10}"), faker.random().nextInt(1, 1000)));
    }
    return new ItemList(itemList);
  }

  public static ItemList getListWithMediaId(Movie media) {
    return new ItemList(
        Collections.singletonList(new Item(media.getMediaType(), media.getMediaId())));
  }
}
