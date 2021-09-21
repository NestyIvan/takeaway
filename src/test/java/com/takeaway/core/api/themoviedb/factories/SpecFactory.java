package com.takeaway.core.api.themoviedb.factories;

import com.takeaway.core.api.themoviedb.model.ItemList;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.json.JSONObject;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

public class SpecFactory {

  public static ResponseSpecification getListSpecSuccess(JSONObject movieList) {
    return new ResponseSpecBuilder()
        .expectStatusCode(200)
        .expectBody("name", is(movieList.get("name")))
        .expectBody("iso_639_1", is(movieList.get("iso_639_1")))
        .build();
  }

  public static ResponseSpecification getListSpecUnauthorized() {
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

  public static ResponseSpecification getUpdateListSpec() {
    return new ResponseSpecBuilder()
        .expectStatusCode(201)
        .expectBody("success", is(true))
        .expectBody("status_code", is(12))
        .expectBody("status_message", is("The item/record was updated successfully."))
        .build();
  }

  public static ResponseSpecification getUpdateListSpecUnauthorized() {
    return new ResponseSpecBuilder()
        .expectStatusCode(401)
        .expectBody("success", is(false))
        .expectBody("status_code", is(7))
        .expectBody("status_message", is("Invalid API key: You must be granted a valid key."))
        .build();
  }

  public static ResponseSpecification getUpdateListSpecNotFound() {
    return new ResponseSpecBuilder()
        .expectStatusCode(404)
        .expectBody("success", is(false))
        .expectBody("status_code", is(34))
        .expectBody("status_message", is("The resource you requested could not be found."))
        .build();
  }

  public static ResponseSpecification getAddItemsSpecSuccess(ItemList itemList) {
    return new ResponseSpecBuilder()
        .expectStatusCode(200)
        .expectBody("status_code", is(1))
        .expectBody("status_message", is("Success."))
        .expectBody("success", is(true))
        .expectBody("results.size()", is(itemList.getItems().size()))
        .build();
  }

  public static ResponseSpecification getClearListSpecSuccess(int listId) {
    return new ResponseSpecBuilder()
        .expectStatusCode(200)
        .expectBody("status_code", is(1))
        .expectBody("status_message", is("Success."))
        .expectBody("success", is(true))
        .expectBody("id", is(listId))
        .expectBody("items_deleted", greaterThan(0))
        .build();
  }
}
