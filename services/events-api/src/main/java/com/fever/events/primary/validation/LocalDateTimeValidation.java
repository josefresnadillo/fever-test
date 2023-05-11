package com.fever.events.primary.validation;

import com.fever.exceptions.domain.RequestValidation;
import com.fever.exceptions.domain.model.FeverErrorType;
import com.fever.utils.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class LocalDateTimeValidation {
    private static final String DATE_ERROR = "%s: invalid date: %s Format: %s";
    public static final String QUERY_PARAM_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    private LocalDateTimeValidation() {
        // do nothing
    }
    public static void validateOptional(final String date,
                                        final String fieldName,
                                        final RequestValidation.Builder requestValidation) {
        if (StringUtils.isNotNullOrEmptyOrWhiteSpace(date)) {
            validateMandatory(date, fieldName, requestValidation);
        }
    }
    public static void validateMandatory(final String date,
                                         final String fieldName,
                                         final RequestValidation.Builder requestValidation) {
        try {
            LocalDateTime.parse(date, DateTimeFormatter.ofPattern(QUERY_PARAM_DATE_FORMAT));
        } catch (Exception e) {
            requestValidation.badRequestError(
                    FeverErrorType.DATE_NOT_VALID,
                    String.format(DATE_ERROR, fieldName, date, QUERY_PARAM_DATE_FORMAT));
        }
    }
}