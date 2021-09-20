package com.takeaway.core.api.themoviedb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ItemList {
  private List<Item> items;
}
