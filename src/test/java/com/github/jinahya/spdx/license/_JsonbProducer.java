package com.github.jinahya.spdx.license;

import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class _JsonbProducer {

    // -----------------------------------------------------------------------------------------------------------------
    @Produces
    Jsonb produceJsonb() {
        return JsonbBuilder.create();
    }

    void disposeJsonb(final @Disposes Jsonb jsonb) throws Exception {
        log.debug("disposing {}", jsonb);
        jsonb.close();
    }
}
