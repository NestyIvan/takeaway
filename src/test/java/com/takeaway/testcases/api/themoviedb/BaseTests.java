package com.takeaway.testcases.api.themoviedb;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.BeforeClass;

public class BaseTests {
  @BeforeClass
  public static void setUp() {
    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
  }
}
