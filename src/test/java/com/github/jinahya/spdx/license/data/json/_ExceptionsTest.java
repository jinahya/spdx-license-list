package com.github.jinahya.spdx.license.data.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jinahya.spdx.license.util.ObjectIoUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class _ExceptionsTest {

    @Test
    void __() throws IOException, ClassNotFoundException {
        final var rootDirectory = Stream.concat(Stream.of(".", "src", "main", "resources"),
                                                Arrays.stream(getClass().getPackageName().split("\\.")))
                .map(Path::of)
                .reduce(Path::resolve)
                .orElseThrow()
                .toAbsolutePath()
                .normalize();
        log.debug("d: {}", rootDirectory);
        rootDirectory.toFile().mkdirs();
        // -------------------------------------------------------------------------------------------------------------
        final var mapper = new ObjectMapper().findAndRegisterModules();
        // https://stackoverflow.com/a/7108530/330457
        mapper.setVisibility(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                                     .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                                     .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                                     .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                                     .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
        // -------------------------------------------------------------------------------------------------------------
        final _Exceptions exceptions;
        {
            final var name = "/json/exceptions.json";
            try (var resource = getClass().getResourceAsStream(name)) {
                assertThat(resource)
                        .as("resource for '%1$s'", name)
                        .isNotNull();
                exceptions = mapper.reader().readValue(resource, _Exceptions.class);
                {
                    final var file = rootDirectory.resolve(_Exceptions.RESOURCE_NAME).toFile();
                    ObjectIoUtils.write(file, exceptions);
                }
            }
            final var instance = _Exceptions.getInstance();
            instance.getExceptions().forEach((k, v) -> {
                assertThat(instance.getException(k)).isSameAs(v);
            });
        }
        // ---------------------------------------------------------------------------------------------------------
        final var detailsDirectory = rootDirectory.resolve("exceptions");
        detailsDirectory.toFile().mkdirs();
        for (final var entry : exceptions.getExceptions().entrySet()) {
            final var name = "/json/exceptions/" + entry.getKey() + ".json";
            try (var resource = getClass().getResourceAsStream(name)) {
                assertThat(resource)
                        .as("detail resource for '%1$s'", name)
                        .isNotNull();
                final var exception = mapper.reader().readValue(resource, _Exception.class);
                final var file = detailsDirectory.resolve(exception.getLicenseExceptionId() + ".bin").toFile();
                ObjectIoUtils.write(file, exception);
                assertThat((_Exception) ObjectIoUtils.read(file)).isEqualTo(exception);
            }
        }
    }
}
