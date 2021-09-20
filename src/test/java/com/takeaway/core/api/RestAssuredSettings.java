package com.takeaway.core.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.Getter;

public class RestAssuredSettings {

  public static final String BASE_URI = System.getProperty("api.base.uri");
  private static final String TOKEN = System.getProperty("api.access.token");
  public static final String API_KEY = System.getProperty("api.key");

  @Getter private RequestSpecification requestSpec;
  @Getter private ResponseSpecification responseSpec;

  public RestAssuredSettings() {

    requestSpec =
        new RequestSpecBuilder()
            .setBaseUri(BASE_URI)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    responseSpec = new ResponseSpecBuilder().build();
  }

  public static final RequestSpecification requestSpecNoAuth =
      new RequestSpecBuilder()
          .setBaseUri(BASE_URI)
          .setAccept(ContentType.JSON)
          .setContentType(ContentType.JSON)
          .log(LogDetail.ALL)
          .build();

  public static RequestSpecification getRequestSpecWithAuth() {
    return new RequestSpecBuilder()
        .setBaseUri(BASE_URI)
        .setAccept(ContentType.JSON)
        .addHeader("Authorization", "Bearer " + TOKEN)
        .setContentType(ContentType.JSON)
        .log(LogDetail.ALL)
        .build();
  }
}
