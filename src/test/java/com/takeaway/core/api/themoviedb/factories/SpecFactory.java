package com.takeaway.core.api.themoviedb.factories;

import com.takeaway.core.api.themoviedb.model.MovieList;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import java.util.List;

import static org.hamcrest.Matchers.is;

public class SpecFactory {

  public static ResponseSpecification getListSpecSuccess(MovieList movieList) {
    return new ResponseSpecBuilder()
        .expectStatusCode(200)
        .expectBody("name", is(movieList.getName()))
        .expectBody("iso_639_1", is(movieList.getIso6391()))
        .build();
  }

  public static ResponseSpecification getListIsPrivateSpec() {
    return new ResponseSpecBuilder()
        .expectStatusCode(401)
        .expectBody("success", is(false))
        .expectBody("status_code", is(39))
        .expectBody("status_message", is("This resource is private."))
        .build();
  }

  public static ResponseSpecification getCreateListSpec() {
    return new ResponseSpecBuilder().expectStatusCode(201).expectBody("success", is(true)).build();
  }

  public static ResponseSpecification getCreateListUnauthorizedSpec() {
    return new ResponseSpecBuilder()
        .expectStatusCode(401)
        .expectBody("success", is(false))
        .expectBody("status_code", is(36))
        .expectBody(
            "status_message", is("This token hasn't been granted write permission by the user."))
        .expectBody(
            "error_message",
            is("You must specify an Authorization header with a Bearer token to proceed."))
        .build();
  }

  public static ResponseSpecification getUpdateListSpec() {
    return new ResponseSpecBuilder()
        .expectStatusCode(201)
        .expectBody("success", is(true))
        .expectBody("status_code", is(12))
        .expectBody("status_message", is("The item/record was updated successfully."))
        .build();
  }

  public static ResponseSpecification getSpecUnauthorized() {
    return new ResponseSpecBuilder()
        .expectStatusCode(401)
        .expectBody("success", is(false))
        .expectBody("status_code", is(7))
        .expectBody("status_message", is("Invalid API key: You must be granted a valid key."))
        .build();
  }

  public static ResponseSpecification getSpecNotFound() {
    return new ResponseSpecBuilder()
        .expectStatusCode(404)
        .expectBody("success", is(false))
        .expectBody("status_code", is(34))
        .expectBody("status_message", is("The resource you requested could not be found."))
        .build();
  }

  public static ResponseSpecification getAddItemsSpecSuccess() {
    return new ResponseSpecBuilder()
        .expectStatusCode(200)
        .expectBody("status_code", is(1))
        .expectBody("status_message", is("Success."))
        .expectBody("success", is(true))
        .build();
  }

  public static ResponseSpecification getUpdateItemsSpecFail(List<Integer> failedItemIds) {
    return new ResponseSpecBuilder()
        .expectStatusCode(200)
        .expectBody("status_code", is(1))
        .expectBody("status_message", is("Success."))
        .expectBody("success", is(true))
        .expectBody(
            "results.findAll( { element -> element.success == false }).size()",
            is(failedItemIds.size()))
        .build();
  }

  public static ResponseSpecification getClearListSpecSuccess(int listId) {
    return new ResponseSpecBuilder()
        .expectStatusCode(200)
        .expectBody("status_code", is(1))
        .expectBody("status_message", is("Success."))
        .expectBody("success", is(true))
        .expectBody("id", is(listId))
        .build();
  }
}
