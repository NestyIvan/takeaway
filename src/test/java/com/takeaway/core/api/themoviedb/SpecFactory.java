package com.takeaway.core.api.themoviedb;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.is;

public class SpecFactory {

  public static ResponseSpecification getCreateListSpec() {
    return new ResponseSpecBuilder().expectStatusCode(201).expectBody("success", is(true)).build();
  }
}
