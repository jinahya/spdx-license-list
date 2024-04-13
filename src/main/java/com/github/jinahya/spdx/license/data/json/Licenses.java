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
public final class Licenses
        implements Serializable {

    private static final long serialVersionUID = 1668175366953366612L;

    // -----------------------------------------------------------------------------------------------------------------
    static final String LICENSES_RESOURCE_NAME = "licenses.bin";

    // -----------------------------------------------------------------------------------------------------------------
    private static final class InstanceHolder {

        private static final Licenses INSTANCE;

        static {
            final var resource = Licenses.class.getResource(LICENSES_RESOURCE_NAME);
            assert resource != null;
            try {
                INSTANCE = IoUtils.read(new File(resource.toURI()));
            } catch (final Exception e) {
                throw new InstantiationError("failed to load from `" + LICENSES_RESOURCE_NAME + "'");
            }
        }
    }

    static String detailResourceName(final String licenseId) {
        return "details/" + licenseId + ".bin";
    }

    private static License detail(final String licenseId) {
        final var name = detailResourceName(licenseId);
        final var resource = Licenses.class.getResource(name);
        assert resource != null;
        try {
            return IoUtils.read(new File(resource.toURI()));
        } catch (final Exception e) {
            throw new InstantiationError("failed to load from `" + name + "'");
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns the instance of this class.
     *
     * @return the instance of this class.
     */
    public static Licenses getInstance() {
        return InstanceHolder.INSTANCE;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    private Licenses() {
        super();
//        throw new IllegalArgumentException("");
    }

    // -----------------------------------------------------------------------------------------------------------------
    // https://stackoverflow.com/a/3343314/330457
    private void readObject(final ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        assert licenses != null;
        map();
    }

    private void map() {
        if (licenses == null) {
            licenses = new ArrayList<>();
        }
        simple = licenses.stream()
                .collect(Collectors.toMap(License::getLicenseId, Function.identity()));
        detail = simple.keySet().stream()
                .map(Licenses::detail)
                .collect(Collectors.toMap(License::getLicenseId, Function.identity()));
    }

    // -------------------------------------------------------------------------------------------------- licenseVersion

    /**
     * Returns the {@code licenseListVersion} of this instance.
     *
     * @return the {@code licenseListVersion} of this instance.
     */
    public String getLicenseListVersion() {
        return licenseListVersion;
    }

    // -------------------------------------------------------------------------------------------------------- licenses

    /**
     * Returns an unmodifiable map of license-ids and licenses.
     *
     * @param detailed a flag for resulting detailed instances.
     * @return an unmodifiable map of license-ids and licenses.
     */
    public Map<String, License> getLicenses(final boolean detailed) {
        if (simple == null) {
            map();
        }
        return simple;
    }

    /**
     * Returns the license associated with specified license-id.
     *
     * @param licenseId the license-id.
     * @param detailed    a flag for resulting detailed instance.
     * @return the license associated with {@code licenseId}; {@code null} when no license matches.
     */
    public License getLicense(final String licenseId, final boolean detailed) {
        Objects.requireNonNull(licenseId, "licenseId is null");
        return getLicenses(detailed).get(licenseId);
    }

    // ----------------------------------------------------------------------------------------------------- releaseDate

    /**
     * Returns the {@code releaseDate} of this instance.
     *
     * @return the {@code releaseDate} of this instance.
     */
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Setter(AccessLevel.NONE)
    private String licenseListVersion;

    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    private List<License> licenses;

    @Setter(AccessLevel.NONE)
    private LocalDate releaseDate;

    // -----------------------------------------------------------------------------------------------------------------
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private transient Map<String, License> simple;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private transient Map<String, License> detail;
}
