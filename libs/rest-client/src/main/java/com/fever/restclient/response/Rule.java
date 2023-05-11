package com.fever.restclient.response;

import com.fever.restclient.model.RequestOptions;
import io.reactivex.Single;
import io.vertx.reactivex.core.buffer.Buffer;
import io.vertx.reactivex.ext.web.client.HttpResponse;

import java.util.function.BiFunction;

public interface Rule extends BiFunction<HttpResponse<Buffer>, RequestOptions, Single<HttpResponse<Buffer>>> {
}
