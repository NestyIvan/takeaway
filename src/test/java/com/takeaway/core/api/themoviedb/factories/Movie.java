package com.takeaway.core.api.themoviedb.factories;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Movie {
  FIGHT_CLUB(550, "movie");

  private int mediaId;
  private String mediaType;
}
