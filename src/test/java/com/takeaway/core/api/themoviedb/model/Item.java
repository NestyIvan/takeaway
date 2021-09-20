package com.takeaway.core.api.themoviedb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Item {
  private String media_type;
  private int media_id;
}
