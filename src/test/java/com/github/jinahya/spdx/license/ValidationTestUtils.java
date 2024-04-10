package com.github.jinahya.spdx.license;

import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;

public final class ValidationTestUtils {

    private static final ValidatorFactory VALIDATOR_FACTORY = Validation.buildDefaultValidatorFactory();

    private ValidationTestUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
