package com.fever.resthandler.model;

import java.util.List;
import java.util.Objects;

public class Page<T> {
  private final List<T> result;
  private final int totalSize;
  private final int resultSize;

  public Page(final List<T> result, final int totalSize, final int resultSize) {
    this.result = result;
    this.totalSize = totalSize;
    this.resultSize = resultSize;
  }

  public List<T> getResult() {
    return result;
  }

  public int getTotalSize() {
    return totalSize;
  }

  public int getResultSize() {
    return resultSize;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Page<?> page = (Page<?>) o;
    return totalSize == page.totalSize && resultSize == page.resultSize && Objects.equals(result, page.result);
  }

  @Override
  public int hashCode() {
    return Objects.hash(result, totalSize, resultSize);
  }

  @Override
  public String toString() {
    return "Page{" +
        "result=" + result +
        ", totalSize=" + totalSize +
        ", resultSize=" + resultSize +
        '}';
  }
}
