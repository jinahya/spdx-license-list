package com.github.jinahya.spdx.license;

import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class _ValidatorProducer {

    // -----------------------------------------------------------------------------------------------------------------
    @Produces
    ValidatorFactory produceValidatorFactory() {
        return Validation.buildDefaultValidatorFactory();
    }

    void disposeValidatorFactory(final @Disposes ValidatorFactory validatorFactory) {
        log.debug("disposing {}", validatorFactory);
        validatorFactory.close();
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Produces
    Validator produceValidator(final ValidatorFactory validatorFactory) {
        return validatorFactory.getValidator();
    }

    void disposeValidator(final @Disposes Validator validator) {
        log.debug("disposing {}", validator);
    }
}
