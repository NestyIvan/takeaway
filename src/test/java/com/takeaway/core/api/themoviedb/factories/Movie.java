package com.takeaway.core.api.themoviedb.factories;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@AllArgsConstructor
@Getter
public enum Movie {
  FIGHT_CLUB(550, "movie"),
  JUSTICE_LEAGUE(791373, "movie"),
  TENET(577922, "movie");

  private int mediaId;
  private String mediaType;

  public static List<Movie> getAllMovies() {
    return new ArrayList<>(EnumSet.allOf(Movie.class));
  }
}
