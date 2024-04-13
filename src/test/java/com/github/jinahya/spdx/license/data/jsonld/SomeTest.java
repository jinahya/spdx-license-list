package com.github.jinahya.spdx.license.data.jsonld;

import com.apicatalog.jsonld.document.JsonDocument;
import com.github.jsonldjava.core.JsonLdOptions;
import com.github.jsonldjava.core.JsonLdProcessor;
import com.github.jsonldjava.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class SomeTest {

    static void __(final Consumer<? super InputStream> consumer) throws Exception {
        final var index = SomeTest.class.getResource("index.txt");
        assertThat(index).isNotNull();
        try (var lines = Files.lines(new File(index.toURI()).toPath())) {
            lines.forEach(l -> {
                log.debug("line: {}", l);
                final var name = "/jsonld/" + l;
                try (var resource = SomeTest.class.getResourceAsStream(name)) {
                    assertThat(resource)
                            .as("resource for '%1$s'", name)
                            .isNotNull();
                    consumer.accept(resource);
                } catch (final IOException ioe) {
                    throw new RuntimeException(ioe);
                }
            });
        }
    }

    @Test
    void __() throws Exception {
        final var index = getClass().getResource("index.txt");
        assertThat(index).isNotNull();
        try (var lines = Files.lines(new File(index.toURI()).toPath())) {
            lines.forEach(l -> {
                log.debug("line: {}", l);
                final var name = "/jsonld/" + l;
                try (var resource = getClass().getResourceAsStream(name)) {
                    assertThat(resource)
                            .as("resource for '%1$s'", name)
                            .isNotNull();
                    final var object = JsonUtils.fromInputStream(resource);
                    log.debug("object: {}", object.getClass());
                    final var context = new HashMap<String, Object>();
                    final var options = new JsonLdOptions();
                    final var compact = JsonLdProcessor.compact(object, context, options);
                    log.debug("compact: {}", compact.getClass());
//                    System.out.println(JsonUtils.toPrettyString(compact));
                } catch (final IOException ioe) {
                    throw new RuntimeException(ioe);
                }
            });
        }
    }

    @Test
    void __JsonLd() throws Exception {
        __(r -> {
            try {
                final var document = JsonDocument.of(r);
                log.debug("document: {}", document);
            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
