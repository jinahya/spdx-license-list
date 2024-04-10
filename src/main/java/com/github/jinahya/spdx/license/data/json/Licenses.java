package com.github.jinahya.spdx.license.data.json;

import com.github.jinahya.spdx.license.util.ObjectIoUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public final class Licenses
        implements Serializable {

    private static final long serialVersionUID = 1668175366953366612L;

    // -----------------------------------------------------------------------------------------------------------------
    static final String NAME = "licenses.bin";

    // -----------------------------------------------------------------------------------------------------------------
    public static Licenses getInstance() {
        final var resource = Licenses.class.getResource(NAME);
        assert resource != null;
        try {
            return ObjectIoUtils.read(new File(resource.toURI()));
        } catch (final Exception e) {
            throw new RuntimeException("failed to load resource", e);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // https://stackoverflow.com/a/3343314/330457
    private void readObject(final ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        assert licenses != null;
        map = licenses.stream().collect(Collectors.toMap(License::getLicenseId, Function.identity()));
    }

    // -------------------------------------------------------------------------------------------------------- licenses
    public License getLicense(final String id) {
        Objects.requireNonNull(id, "id is null");
        return map.get(id);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @NotEmpty
    private String licenseListVersion;

    @NotNull
    @ToString.Exclude
    private @NotEmpty List<@Valid @NotNull License> licenses;

    @PastOrPresent
    @NotNull
    private LocalDate releaseDate;

    // -----------------------------------------------------------------------------------------------------------------
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private transient Map<String, License> map;
}
