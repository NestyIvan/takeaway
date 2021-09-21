package com.takeaway.core.api.themoviedb.factories;

import com.github.javafaker.Faker;
import com.takeaway.core.api.themoviedb.model.MovieList;

public class MovieListFactory {
  private static final Faker faker = new Faker();

  public static MovieList getDefaultList() {
    return MovieList.builder()
        .name(faker.regexify("[a-z]{10}"))
        .iso6391("en")
        .publicValue(true)
        .build();
  }

  public static MovieList getListWithAllFields() {
    return MovieList.builder()
        .name(faker.regexify("[a-z]{10}"))
        .iso6391("en")
        .description(faker.chuckNorris().fact())
        .publicValue(true)
        .iso31661("EN")
        .build();
  }

  public static MovieList getListWithOptionalFieldsOnly() {
    return MovieList.builder()
        .description(faker.chuckNorris().fact())
        .publicValue(true)
        .iso31661("EN")
        .build();
  }

  public static MovieList getListWithAccess(boolean isPublic) {
    return MovieList.builder()
        .name(faker.regexify("[a-z]{10}"))
        .iso6391("en")
        .publicValue(isPublic)
        .build();
  }
}
