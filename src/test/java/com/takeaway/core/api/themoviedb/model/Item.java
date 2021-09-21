package com.takeaway.core.api.themoviedb.model;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Item {
  private String media_type;
  private int media_id;
  private final String comment = new Faker().rickAndMorty().quote();
}
