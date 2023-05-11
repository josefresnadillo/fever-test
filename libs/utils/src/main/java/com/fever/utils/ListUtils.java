package com.fever.utils;

import java.util.*;
import java.util.stream.Collectors;

public class ListUtils {

  private ListUtils() {
    // do nothing
  }

  public static <T> List<T> castList(final Object[] objects, final Class<T> clazz) {
    return Arrays.stream(objects)
        .map(clazz::cast)
        .collect(Collectors.toList());
  }

  public static <T> List<T> listOrEmpty(final List<T> list) {
    return list != null ?
        list:
        List.of();
  }
}
