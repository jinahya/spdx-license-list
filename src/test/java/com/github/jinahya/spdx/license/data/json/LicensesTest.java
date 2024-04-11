package com.github.jinahya.spdx.license.data.json;

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
class LicensesTest {

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
        // -------------------------------------------------------------------------------------------------------------
        final Licenses licenses;
        {
            final var name = "/json/licenses.json";
            try (var resource = getClass().getResourceAsStream(name)) {
                assertThat(resource)
                        .as("resource for '%1$s'", name)
                        .isNotNull();
                licenses = mapper.reader().readValue(resource, Licenses.class);
                final var file = rootDirectory.resolve(Licenses.NAME).toFile();
                ObjectIoUtils.write(file, licenses);
            }
            final var instance = Licenses.getInstance();
            instance.getLicenses().forEach((k, v) -> {
                assertThat(instance.getLicense(k)).isSameAs(v);
            });
        }
        // ---------------------------------------------------------------------------------------------------------
        final var detailsDirectory = rootDirectory.resolve("details");
        detailsDirectory.toFile().mkdirs();
        for (final var entry : licenses.getLicenses().entrySet()) {
            final var name = "/json/details/" + entry.getKey() + ".json";
            try (var resource = getClass().getResourceAsStream(name)) {
                assertThat(resource)
                        .as("detail resource for '%1$s'", name)
                        .isNotNull();
                final var license = mapper.reader().readValue(resource, License.class);
                final var file = detailsDirectory.resolve(license.getLicenseId() + ".bin").toFile();
                ObjectIoUtils.write(file, license);
                assertThat((License) ObjectIoUtils.read(file)).isEqualTo(license);
            }
        }
    }
}
