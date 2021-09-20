package com.takeaway.core.api.themoviedb.factories;

import com.takeaway.core.api.themoviedb.model.MovieListResult;

public class MovieListResultFactory {

  public static MovieListResult getResultForFightClub() {
    return MovieListResult.builder()
        .adult(false)
        .id(550)
        .media_type("movie")
        .original_language("en")
        .original_title("Fight Club")
        .release_date("1999-10-15")
        .title("Fight Club")
        .build();
  }
}
