package com.takeaway.core.api.themoviedb;

public class EndPoints {

  public static final String GET_LIST = "/list/{listId}?page={pageId}&api_key={apiKey}";
  public static final String CREATE_LIST = "/list?api_key={apiKey}";
  public static final String DELETE_LIST = "/list/{listId}?api_key={apiKey}";
}
