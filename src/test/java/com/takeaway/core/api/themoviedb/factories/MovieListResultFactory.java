package com.takeaway.core.api.themoviedb.factories;

import com.takeaway.core.api.themoviedb.model.MovieListResult;

public class MovieListResultFactory {

  public static MovieListResult getResultForMovie(Movie movie) {
    if (movie == Movie.FIGHT_CLUB) {
      return getResultForFightClub();
    } else if (movie == Movie.JUSTICE_LEAGUE) {
      return getResultForJusticeLeague();
    } else if (movie == Movie.TENET) {
      return getResultForTenet();
    }
    throw new IllegalArgumentException("No such movie id: " + movie.getMediaId());
  }

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

  public static MovieListResult getResultForJusticeLeague() {
    return MovieListResult.builder()
        .adult(false)
        .id(791373)
        .media_type("movie")
        .original_language("en")
        .original_title("Zack Snyder's Justice League")
        .release_date("2021-03-18")
        .title("Zack Snyder's Justice League")
        .build();
  }

  public static MovieListResult getResultForTenet() {
    return MovieListResult.builder()
        .adult(false)
        .id(577922)
        .media_type("movie")
        .original_language("en")
        .original_title("Tenet")
        .release_date("2020-08-22")
        .title("Tenet")
        .build();
  }
}
