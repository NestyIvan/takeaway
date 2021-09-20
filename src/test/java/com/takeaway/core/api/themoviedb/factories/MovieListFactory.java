package com.takeaway.core.api.themoviedb.factories;

import com.github.javafaker.Faker;
import org.json.JSONObject;

public class MovieListFactory {
  private static final Faker faker = new Faker();

  public static JSONObject getDefaultList() {
    return new JSONObject().put("name", faker.regexify("[a-z1-9]{10}")).put("iso_639_1", "en");
  }

  public static JSONObject getListWithAllFields() {
    return new JSONObject()
        .put("name", faker.regexify("[a-z1-9]{10}"))
        .put("iso_639_1", "en")
        .put("description", faker.rickAndMorty().quote())
        .put("public", true)
        .put("iso_3166_1", "EN");
  }

  public static JSONObject getListWithAccess(boolean isPublic) {
    return new JSONObject()
        .put("name", faker.regexify("[a-z1-9]{10}"))
        .put("iso_639_1", "en")
        .put("public", isPublic);
  }
}
