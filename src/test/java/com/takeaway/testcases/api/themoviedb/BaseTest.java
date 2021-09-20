package com.takeaway.testcases.api.themoviedb;

import com.takeaway.core.api.RestAssuredSettings;
import io.restassured.RestAssured;
import org.junit.BeforeClass;

public class BaseTest {
  @BeforeClass
  public static void setUp() {
    RestAssuredSettings restAssuredSettings = new RestAssuredSettings();
    RestAssured.requestSpecification = restAssuredSettings.getRequestSpec();
    RestAssured.responseSpecification = restAssuredSettings.getResponseSpec();
  }
}
