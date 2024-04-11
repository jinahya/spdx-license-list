package com.github.jinahya.spdx.license.data.json;

import com.github.jinahya.spdx.license.util.ObjectIoUtils;
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

@EqualsAndHashCode
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public final class _Exceptions
        implements Serializable {

    private static final long serialVersionUID = -7925674913511699783L;

    // -----------------------------------------------------------------------------------------------------------------
    static final String RESOURCE_NAME = "exceptions.bin";

    // -----------------------------------------------------------------------------------------------------------------
    private static final class InstanceHolder {

        private static final _Exceptions INSTANCE;

        static {
            final var resource = _Exceptions.class.getResource(RESOURCE_NAME);
            assert resource != null;
            try {
                INSTANCE = ObjectIoUtils.read(new File(resource.toURI()));
            } catch (final Exception e) {
                throw new RuntimeException("failed to load resource", e);
            }
        }
    }

    public static _Exceptions getInstance() {
        return InstanceHolder.INSTANCE;
    }

    // -----------------------------------------------------------------------------------------------------------------
    // https://stackoverflow.com/a/3343314/330457
    private void readObject(final ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        assert exceptions != null;
        map();
    }

    private void map() {
        map = exceptions.stream().collect(Collectors.toMap(_Exception::getLicenseExceptionId, Function.identity()));
    }

    // -----------------------------------------------------------------------------------------------------------------

    public String getLicenseListVersion() {
        return licenseListVersion;
    }

    // -------------------------------------------------------------------------------------------------------- licenses
    public Map<String, _Exception> getExceptions() {
        if (map == null) {
            map();
        }
        return map;
    }

    public _Exception getException(final String licenseExceptionId) {
        Objects.requireNonNull(licenseExceptionId, "licenseExceptionId is null");
        return getExceptions().get(licenseExceptionId);
    }

    // -----------------------------------------------------------------------------------------------------------------
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Setter(AccessLevel.NONE)
    @Getter
    private String licenseListVersion;

    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private List<_Exception> exceptions;

    @Setter(AccessLevel.NONE)
    @Getter
    private LocalDate releaseDate;

    // -----------------------------------------------------------------------------------------------------------------
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private transient Map<String, _Exception> map;
}
