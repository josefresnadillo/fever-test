package com.fever.events.primary.validation;

import com.fever.exceptions.domain.RequestValidation;
import com.fever.utils.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LocalDateTimeValidationTest {

    @BeforeAll
    public static void setUp() {
        // do nothing
    }

    @Test
    void givenEmptyDate_WhenValidOptional_then_validationIsOk() {
        assertDoesNotThrow(() -> LocalDateTimeValidation.validateOptional(StringUtils.EMPTY, StringUtils.EMPTY, new RequestValidation.Builder()));
    }

    @Test
    void givenEmptyDate_WhenValidMandatory_then_validationIsNotOk() {
        var requestValidator = new RequestValidation.Builder();
        LocalDateTimeValidation.validateMandatory(StringUtils.EMPTY, StringUtils.EMPTY, requestValidator);
        assertTrue(requestValidator.build().hasErrors());
    }

    @Test
    void givenValidDate_WhenValidMandatory_then_validationIsOk() {
        var requestValidator = new RequestValidation.Builder();
        LocalDateTimeValidation.validateMandatory("2021-07-30T21:00:00", StringUtils.EMPTY, requestValidator);
        assertFalse(requestValidator.build().hasErrors());
    }

    @Test
    void givenInvalidDate_WhenValidMandatory_then_validationIsNotOk() {
        var requestValidator = new RequestValidation.Builder();
        LocalDateTimeValidation.validateMandatory("wrong", StringUtils.EMPTY, requestValidator);
        assertTrue(requestValidator.build().hasErrors());
    }
}
