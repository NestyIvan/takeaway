package com.takeaway.core.api.themoviedb;

public class EndPoints {

  public static final String GET_LIST = "/list/{listId}?page={pageId}&api_key={apiKey}";
  public static final String CREATE_LIST = "/list?api_key={apiKey}";
  public static final String UPDATE_LIST = "/list/{listId}";
  public static final String CLEAR_LIST = "/list/{listId}/clear";
  public static final String DELETE_LIST = "/list/{listId}?api_key={apiKey}";

  public static final String ADD_ITEMS = "/list/{listId}/items";
  public static final String UPDATE_ITEMS = "/list/{listId}/items";
  public static final String DELETE_ITEMS = "/list/{listId}/items";
}
