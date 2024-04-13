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

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class LicensesTest {

    @Test
    void __() throws IOException, ClassNotFoundException {
        final var root = Stream.concat(
                        Stream.of(".", "src", "main", "resources"),
                        Arrays.stream(getClass().getPackageName().split("\\.")))
                .map(Path::of)
                .reduce(Path::resolve)
                .orElseThrow()
                .toAbsolutePath()
                .normalize();
        {
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
            final Licenses deserialized;
            {
                final var name = "/json/licenses.json";
                try (var resource = getClass().getResourceAsStream(name)) {
                    assertThat(resource)
                            .as("resource for '%1$s'", name)
                            .isNotNull();
                    deserialized = mapper.reader().readValue(resource, Licenses.class);
                    final var file = root.resolve(Licenses.LICENSES_RESOURCE_NAME).toFile();
                    IoUtils.write(file, deserialized);
                }
            }
            // ---------------------------------------------------------------------------------------------------------
            {
                final var details = root.resolve("details");
                details.toFile().mkdirs();
                for (final var simpleLicense : deserialized.getLicenses()) {
                    final var detailName = "/json/details/" + simpleLicense.getLicenseId() + ".json";
                    try (var detailResource = getClass().getResourceAsStream(detailName)) {
                        assertThat(detailResource)
                                .as("resource for '%1$s'", detailName)
                                .isNotNull();
                        final var license = mapper.reader().readValue(detailResource, License.class);
                        final var file = details.resolve(license.getLicenseId() + ".ser").toFile();
                        IoUtils.write(file, license);
                        assertThat((License) IoUtils.read(file)).isEqualTo(license);
                    }
                }
            }
        }
        // -------------------------------------------------------------------------------------------------------------
        if (getClass().getResource(Licenses.LICENSES_RESOURCE_NAME) != null) {
            final var instance = Licenses.getInstance();
            final var simple = instance.getLicenses(false);
            for (var entry : simple.entrySet()) {
                final var value = instance.getLicense(entry.getKey(), false);
                assertThat(value).isNotNull();
            }
            final var detail = instance.getLicenses(true);
            assertThat(detail).hasSize(simple.size());
            for (var entry : detail.entrySet()) {
                final var value = instance.getLicense(entry.getKey(), true);
                assertThat(value).isNotNull();
            }
        }
    }
}
