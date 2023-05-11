package com.fever.resthandler.model.criteria;


public class PaginationSearch {

  private final int offset;
  private final int limit;

  private PaginationSearch(int offset, int limit) {
    this.offset = offset;
    this.limit = limit;
  }

  public int getOffset() {
    return offset;
  }

  public int getLimit() {
    return limit;
  }

  public int begin(final int totalSize) {
    return Math.min(getOffset(), totalSize);
  }

  public int end(final int totalSize, final int maxResultSize) {
    final int end = getOffset() + Math.min(getLimit(), maxResultSize);
    return Math.min(end, totalSize);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private int offset = 0;
    private int limit = 1000;

    public Builder() {
      // do nothing
    }

    public Builder offset(String offset) {
      this.offset = offset != null ? Integer.parseInt(offset):this.offset;
      return this;
    }

    public Builder limit(String limit) {
      this.limit = limit != null ? Integer.parseInt(limit):this.limit;
      return this;
    }

    public PaginationSearch build() {
      return new PaginationSearch(offset, limit);
    }
  }
}
