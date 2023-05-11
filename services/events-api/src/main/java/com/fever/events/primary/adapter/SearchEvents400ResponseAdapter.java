package com.fever.events.primary.adapter;

import com.fever.events.primary.api.EventList;
import com.fever.events.primary.api.SearchEvents400Response;
import com.fever.events.primary.api.SearchEvents400ResponseError;
import com.fever.exceptions.domain.model.ErrorMessage;
import com.fever.exceptions.exception.BackendException;

import java.util.List;

public class SearchEvents400ResponseAdapter {

    private SearchEvents400ResponseAdapter(){
        // do nothing
    }

    public static SearchEvents400Response adapt(final BackendException backendException) {
        final ErrorMessage errorMessage = backendException.getFirstErrorMessage();

        final SearchEvents400Response searchEvents400Response = new SearchEvents400Response();

        // Data
        final EventList emptyEventList = new EventList();
        emptyEventList.events(List.of());
        searchEvents400Response.data(emptyEventList);

        // Error with Message and Code
        final SearchEvents400ResponseError error = new SearchEvents400ResponseError();
        error.code(errorMessage.getCode().getValue());
        error.setMessage(errorMessage.getMessage());
        searchEvents400Response.error(error);

        return searchEvents400Response;
    }
}
