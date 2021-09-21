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
        .mediaType("movie")
        .originalLanguage("en")
        .originalTitle("Fight Club")
        .releaseDate("1999-10-15")
        .title("Fight Club")
        .build();
  }

  public static MovieListResult getResultForJusticeLeague() {
    return MovieListResult.builder()
        .adult(false)
        .id(791373)
        .mediaType("movie")
        .originalLanguage("en")
        .originalTitle("Zack Snyder's Justice League")
        .releaseDate("2021-03-18")
        .title("Zack Snyder's Justice League")
        .build();
  }

  public static MovieListResult getResultForTenet() {
    return MovieListResult.builder()
        .adult(false)
        .id(577922)
        .mediaType("movie")
        .originalLanguage("en")
        .originalTitle("Tenet")
        .releaseDate("2020-08-22")
        .title("Tenet")
        .build();
  }
}
