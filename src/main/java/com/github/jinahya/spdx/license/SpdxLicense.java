package com.github.jinahya.spdx.license;

import com.github.jinahya.spdx.license.bind.ExceptionType;
import com.github.jinahya.spdx.license.bind.LicenseType;
import com.github.jinahya.spdx.license.bind.SPDXLicenseCollectionType;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class SpdxLicense {

    private static final String LICENSE_LIST_NAME = "licenses.list";

    private static <R> R applyLicenseStream(final Function<? super Stream<LicenseType>, ? extends R> function)
            throws JAXBException, URISyntaxException, IOException {
        Objects.requireNonNull(function, "function is null");
        final var resource = SpdxLicense.class.getResource(LICENSE_LIST_NAME);
        if (resource == null) {
            throw new RuntimeException("failed to load resource for " + LICENSE_LIST_NAME);
        }
        final var context = JAXBContext.newInstance(SpdxLicenseConstants.BINDING_PACKAGE_NAME);
        final var unmarshaller = context.createUnmarshaller();
        try (var lines = Files.lines(Path.of(resource.toURI()))) {
            return function.apply(
                    lines.map(l -> '/' + l)
                            .map(SpdxLicense.class::getResource)
                            .map(r -> {
                                try {
                                    return unmarshaller.unmarshal(r);
                                } catch (final JAXBException jaxbe) {
                                    throw new RuntimeException("failed to unmarshal from " + r, jaxbe);
                                }
                            })
                            .map(v -> ((JAXBElement<?>) v).getValue())
                            .map(SPDXLicenseCollectionType.class::cast)
                            .map(c -> c.getLicenseOrException().get(0))
                            .map(LicenseType.class::cast)
            );
        }
    }

    private static List<LicenseType> LICENSES_LIST;

    public static List<LicenseType> getLicenseList() {
        var result = LICENSES_LIST;
        if (result == null) {
            try {
//                LICENSES_LIST = result = applyLicenseStream(Stream::toList);
                LICENSES_LIST = result = applyLicenseStream(s -> s.collect(Collectors.toUnmodifiableList()));
            } catch (final Exception e) {
                throw new RuntimeException("failed to load licenses", e);
            }
        }
        return result;
    }

    private static Map<String, LicenseType> LICENSE_MAP;

    public static Map<String, LicenseType> getLicenseMap() {
        var result = LICENSE_MAP;
        if (result == null) {
            try {
                LICENSE_MAP = result = getLicenseList()
                        .stream()
                        .collect(Collectors.toUnmodifiableMap(LicenseType::getLicenseId, Function.identity()));
            } catch (final Exception e) {
                throw new RuntimeException("failed to load licenses", e);
            }
        }
        return result;
    }

    public static LicenseType getLicense(final String licenseId) {
        return getLicenseMap().get(licenseId);
    }

    // -----------------------------------------------------------------------------------------------------------------
    private static final String EXCEPTION_LIST_NAME = "exceptions.list";

    private static <R> R applyExceptionStream(final Function<? super Stream<ExceptionType>, ? extends R> function)
            throws JAXBException, URISyntaxException, IOException {
        Objects.requireNonNull(function, "function is null");
        final var resource = SpdxLicense.class.getResource(EXCEPTION_LIST_NAME);
        if (resource == null) {
            throw new RuntimeException("failed to load resource for " + EXCEPTION_LIST_NAME);
        }
        final var context = JAXBContext.newInstance(SpdxLicenseConstants.BINDING_PACKAGE_NAME);
        final var unmarshaller = context.createUnmarshaller();
        try (var lines = Files.lines(Path.of(resource.toURI()))) {
            return function.apply(
                    lines.map(l -> "/exceptions/" + l)
                            .map(SpdxLicense.class::getResource)
                            .map(r -> {
                                try {
                                    return unmarshaller.unmarshal(r);
                                } catch (final JAXBException jaxbe) {
                                    throw new RuntimeException("failed to unmarshal from " + r, jaxbe);
                                }
                            })
                            .map(v -> ((JAXBElement<?>) v).getValue())
                            .map(SPDXLicenseCollectionType.class::cast)
                            .map(c -> c.getLicenseOrException().get(0))
                            .map(ExceptionType.class::cast)
            );
        }
    }

    private static List<ExceptionType> EXCEPTION_LIST;

    public static List<ExceptionType> getExceptionList() {
        var result = EXCEPTION_LIST;
        if (result == null) {
            try {
                EXCEPTION_LIST = result = applyExceptionStream(s -> s.collect(Collectors.toUnmodifiableList()));
            } catch (final Exception e) {
                throw new RuntimeException("failed to load exceptions", e);
            }
        }
        return result;
    }

    private static Map<String, ExceptionType> EXCEPTION_MAP;

    public static Map<String, ExceptionType> getExceptionMap() {
        var result = EXCEPTION_MAP;
        if (result == null) {
            try {
                EXCEPTION_MAP = result = getExceptionList().stream()
                        .collect(Collectors.toMap(ExceptionType::getLicenseId, Function.identity()));
            } catch (final Exception e) {
                throw new RuntimeException("failed to load exceptions", e);
            }
        }
        return result;
    }

    public static ExceptionType getException(final String exceptionId) {
        return getExceptionMap().get(exceptionId);
    }

    // -----------------------------------------------------------------------------------------------------------------
    private SpdxLicense() {
        throw new AssertionError("instantiation is not allowed");
    }
}
