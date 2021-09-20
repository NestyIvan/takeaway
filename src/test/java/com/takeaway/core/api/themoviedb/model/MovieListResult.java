package com.takeaway.core.api.themoviedb.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;

@Builder
@EqualsAndHashCode
public class MovieListResult {
  private boolean adult;
  private int id;
  private String media_type;
  private String original_language;
  private String original_title;
  private String release_date;
  private String title;
}
