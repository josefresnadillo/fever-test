package com.fever.resthandler.adapter;

import com.fever.resthandler.model.criteria.PaginationSearch;
import io.vertx.reactivex.core.MultiMap;

public class PaginationSearchAdapter {

  private static final String QUERY_OFFSET = "offset";
  private static final String QUERY_LIMIT = "limit";

  private PaginationSearchAdapter() {
    // do  nothing
  }

  public static PaginationSearch adapt(MultiMap queryParams) {
    return PaginationSearch.builder()
        .offset(queryParams.get(QUERY_OFFSET))
        .limit(queryParams.get(QUERY_LIMIT))
        .build();
  }
}
