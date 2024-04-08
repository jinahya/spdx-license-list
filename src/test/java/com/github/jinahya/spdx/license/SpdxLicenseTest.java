package com.github.jinahya.spdx.license;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class SpdxLicenseTest {

    @Test
    void getLicenses__() {
        final var licenses = SpdxLicense.getLicenseMap();
        assertThat(licenses).isNotEmpty();
        licenses.forEach((k, v) -> {
            log.debug("k: {}, v: {}", k, v);
        });
    }

    @Test
    void getLicense__() {
        SpdxLicense.getLicenseMap()
                .forEach((key, value) -> assertThat(SpdxLicense.getLicense(key)).isSameAs(value));
    }

    @Test
    void getExceptions__() {
        final var exceptions = SpdxLicense.getExceptionMap();
        assertThat(exceptions).isNotEmpty();
        exceptions.forEach((k, v) -> {
            log.debug("k: {}, v: {}", k, v);
        });
    }

    @Test
    void getException__() {
        SpdxLicense.getExceptionMap()
                .forEach((key, value) -> assertThat(SpdxLicense.getException(key)).isSameAs(value));
    }
}
