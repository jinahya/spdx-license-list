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
public final class _Exceptions
        implements Serializable {

    private static final long serialVersionUID = -7925674913511699783L;

    // -----------------------------------------------------------------------------------------------------------------
    static final String NAME = "exceptions.bin";

    // -----------------------------------------------------------------------------------------------------------------
    public static _Exceptions getInstance() {
        try {
            final var resource = _Exceptions.class.getResource(NAME);
            assert resource != null;
            return ObjectIoUtils.read(new File(resource.toURI()));
        } catch (final Exception e) {
            throw new RuntimeException("failed to load resource", e);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // https://stackoverflow.com/a/3343314/330457
    private void readObject(final ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        assert exceptions != null;
        map = exceptions.stream().collect(Collectors.toMap(_Exception::getLicenseExceptionId, Function.identity()));
    }

    // -------------------------------------------------------------------------------------------------------- licenses
    public _Exception getException(final String id) {
        Objects.requireNonNull(id, "id is null");
        return map.get(id);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @NotEmpty
    private String licenseListVersion;

    @NotNull
    @ToString.Exclude
    private @NotEmpty List<@Valid @NotNull _Exception> exceptions;

    @PastOrPresent
    @NotNull
    private LocalDate releaseDate;

    // -----------------------------------------------------------------------------------------------------------------
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private transient Map<String, _Exception> map;
}
