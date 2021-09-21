package com.takeaway.core.api.themoviedb.model;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@Builder
@EqualsAndHashCode
public class MovieListResult {
  private boolean adult;

  private int id;

  @SerializedName("media_type")
  private String mediaType;

  @SerializedName("original_language")
  private String originalLanguage;

  @SerializedName("original_title")
  private String originalTitle;

  @SerializedName("release_date")
  private String releaseDate;

  private String title;
}
