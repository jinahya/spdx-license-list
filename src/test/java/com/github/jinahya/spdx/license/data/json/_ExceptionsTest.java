package com.github.jinahya.spdx.license.data.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jinahya.spdx.license.util.IoUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class _ExceptionsTest {

    @Test
    void __() throws IOException, ClassNotFoundException {
        final var root = Stream.concat(Stream.of(".", "src", "main", "resources"),
                                       Arrays.stream(getClass().getPackageName().split("\\.")))
                .map(Path::of)
                .reduce(Path::resolve)
                .orElseThrow()
                .toAbsolutePath()
                .normalize();
        log.debug("d: {}", root);
        root.toFile().mkdirs();
        // -------------------------------------------------------------------------------------------------------------
        final var mapper = new ObjectMapper().findAndRegisterModules();
        // https://stackoverflow.com/a/7108530/330457
        mapper.setVisibility(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                                     .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                                     .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                                     .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                                     .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
        // -------------------------------------------------------------------------------------------------------------
        final _Exceptions deserialized;
        {
            final var name = "/json/exceptions.json";
            try (var resource = getClass().getResourceAsStream(name)) {
                assertThat(resource)
                        .as("resource for '%1$s'", name)
                        .isNotNull();
                deserialized = mapper.reader().readValue(resource, _Exceptions.class);
                {
                    final var file = root.resolve(_Exceptions.EXCEPTIONS_RESOURCE_NAME).toFile();
                    IoUtils.write(file, deserialized);
                    assertThat(IoUtils.<_Exceptions>read(file)).isEqualTo(deserialized);
                }
            }
            final var instance = _Exceptions.getInstance();
            instance.getExceptions(false).forEach((exceptionLicenseId, v) -> {
                assertThat(instance.getException(exceptionLicenseId, false)).isSameAs(v);
            });
        }
        // ---------------------------------------------------------------------------------------------------------
        {
            final var details = root.resolve("exceptions");
            details.toFile().mkdirs();
            for (final var entry : deserialized.getExceptions(false).entrySet()) {
                final var name = "/json/exceptions/" + entry.getKey() + ".json";
                try (var resource = getClass().getResourceAsStream(name)) {
                    assertThat(resource)
                            .as("detail resource for '%1$s'", name)
                            .isNotNull();
                    final var exception = mapper.reader().readValue(resource, _Exception.class);
                    final var file = details.resolve(exception.getLicenseExceptionId() + ".bin").toFile();
                    IoUtils.write(file, exception);
                    assertThat((_Exception) IoUtils.read(file)).isEqualTo(exception);
                }
            }
            // -------------------------------------------------------------------------------------------------------------
            {
                final var instance = _Exceptions.getInstance();
                final var simple = instance.getExceptions(false);
                for (var entry : simple.entrySet()) {
                    final var value = instance.getException(entry.getKey(), false);
                    log.debug("simple: {}", value);
                    assertThat(value).isNotNull();
                }
                final var detail = instance.getExceptions(true);
                assertThat(detail).hasSize(simple.size());
                for (var entry : detail.entrySet()) {
                    final var value = instance.getException(entry.getKey(), true);
                    log.debug("detail: {}", value);
                    assertThat(value).isNotNull();
                }
            }
        }
    }
}
