package com.fever.resthandler.util;

import com.fever.resthandler.model.Page;
import com.fever.utils.ListUtils;
import io.reactivex.Single;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class PageUtils {

  private PageUtils() {
    // do nothing
  }

  public static <T> Single<Page<T>> castPairToPage(final Pair<Integer, List<Single<T>>> pair, final Class<T> clazz) {
    return pair.getRight().isEmpty() ?
        Single.just(new Page<>(List.of(), pair.getLeft(), 0)):
        Single.zip(
            pair.getRight(),
            list -> new Page<>(ListUtils.castList(list, clazz),
                pair.getLeft(),
                pair.getRight().size()));
  }
}
