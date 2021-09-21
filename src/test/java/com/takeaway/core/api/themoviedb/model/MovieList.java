package com.takeaway.core.api.themoviedb.model;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class MovieList {
  private String description;

  private int id;

  @SerializedName("iso_3166_1")
  private String iso31661;

  @SerializedName("iso_639_1")
  private String iso6391;

  private String name;

  private int page;

  private List<MovieListResult> results;

  @SerializedName("public")
  private boolean publicValue;

  private int revenue;

  private int runtime;
}
