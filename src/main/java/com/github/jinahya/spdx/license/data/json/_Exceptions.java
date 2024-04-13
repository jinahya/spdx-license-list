package com.github.jinahya.spdx.license.data.json;

import com.github.jinahya.spdx.license.util.IoUtils;
import lombok.*;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
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
    static final String EXCEPTIONS_RESOURCE_NAME = "exceptions.ser";

    // -----------------------------------------------------------------------------------------------------------------
    private static final class InstanceHolder {

        private static final _Exceptions INSTANCE;

        static {
            final var resource = _Exceptions.class.getResource(EXCEPTIONS_RESOURCE_NAME);
            assert resource != null;
            try {
                INSTANCE = IoUtils.read(new File(resource.toURI()));
            } catch (final Exception e) {
                throw new ExceptionInInitializerError("failed to initialize: " + e.getMessage());
            }
        }
    }

    static String exceptionResourceName(final String exeptionLicenseId) {
        return "exceptions/" + exeptionLicenseId + ".ser";
    }

    private static _Exception detail(final String exceptionLicenseId) {
        final var name = exceptionResourceName(exceptionLicenseId);
        final var resource = _Exceptions.class.getResource(name);
        assert resource != null;
        try {
            return IoUtils.read(new File(resource.toURI()));
        } catch (final Exception e) {
            throw new InstantiationError("failed to load from `" + name + "'");
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

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
        simple = getExceptions().stream()
                .collect(Collectors.toMap(_Exception::getLicenseExceptionId, Function.identity()));
    }

    // ---------------------------------------------------------------------------------------------- licenseListVersion

    public String getLicenseListVersion() {
        return licenseListVersion;
    }

    // -------------------------------------------------------------------------------------------------------- licenses

    List<_Exception> getExceptions() {
        if (exceptions == null) {
            exceptions = new ArrayList<>();
        }
        return exceptions;
    }

    public Map<String, _Exception> getExceptions(final boolean detailed) {
        if (simple == null) {
            map();
        }
        if (detailed) {
            if (detail == null) {
                detail = simple.keySet().stream()
                        .map(_Exceptions::detail)
                        .collect(Collectors.toMap(_Exception::getLicenseExceptionId, Function.identity()));
            }
            return detail;
        }
        return simple;
    }

    public _Exception getException(final String licenseExceptionId, final boolean detailed) {
        Objects.requireNonNull(licenseExceptionId, "licenseExceptionId is null");
        return getExceptions(detailed).get(licenseExceptionId);
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
    private transient Map<String, _Exception> simple;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private transient Map<String, _Exception> detail;
}
