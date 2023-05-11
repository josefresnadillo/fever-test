package com.fever.eventsprovider.client;

import com.fever.eventsprovider.client.model.ProviderEventList;
import io.reactivex.Single;

public interface EventsProviderClient {

  Single<ProviderEventList> listEvents();

}
