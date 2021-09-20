package com.takeaway.core.api.themoviedb.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class MovieList {
  private String description;
  private int id;
  private String iso_3166_1;
  private String iso_639_1;
  private String name;
  private int page;
  private List<MovieListResult> results;
  private int revenue;
  private int runtime;
}
