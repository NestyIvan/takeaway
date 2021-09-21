package com.takeaway.core.api.themoviedb.model;

import com.github.javafaker.Faker;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Item {
  @SerializedName("media_type")
  private String mediaType;

  @SerializedName("media_id")
  private int mediaId;

  private final String comment = new Faker().rickAndMorty().quote();
}
