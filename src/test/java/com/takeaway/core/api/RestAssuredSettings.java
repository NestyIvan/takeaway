package com.takeaway.core.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RestAssuredSettings {

  public static final String BASE_URI = System.getProperty("api.base.uri");
  private static final String TOKEN = System.getProperty("api.access.token");
  public static final String API_KEY = System.getProperty("api.key");

  public static final RequestSpecification requestSpecNoAuth =
      new RequestSpecBuilder()
          .setBaseUri(BASE_URI)
          .setAccept(ContentType.JSON)
          .setContentType(ContentType.JSON)
          .log(LogDetail.ALL)
          .build();

  public static final RequestSpecification requestSpecWithAuth =
      new RequestSpecBuilder()
          .setBaseUri(BASE_URI)
          .setAccept(ContentType.JSON)
          .addHeader("Authorization", "Bearer " + TOKEN)
          .setContentType(ContentType.JSON)
          .log(LogDetail.ALL)
          .build();
}
