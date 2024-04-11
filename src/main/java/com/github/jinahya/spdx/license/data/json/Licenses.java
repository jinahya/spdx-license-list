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
public final class Licenses
        implements Serializable {

    private static final long serialVersionUID = 1668175366953366612L;

    // -----------------------------------------------------------------------------------------------------------------
    static final String RESOURCE_NAME = "licenses.bin";

    // -----------------------------------------------------------------------------------------------------------------
    private static final class InstanceHolder {

        private static final Licenses INSTANCE;

        static {
            final var resource = Licenses.class.getResource(RESOURCE_NAME);
            assert resource != null;
            try {
                INSTANCE = ObjectIoUtils.read(new File(resource.toURI()));
            } catch (final Exception e) {
                throw new InstantiationError("failed to load resource for `" + RESOURCE_NAME + "'");
            }
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
    private Licenses() {
        super();
    }

    // -----------------------------------------------------------------------------------------------------------------
    // https://stackoverflow.com/a/3343314/330457
    private void readObject(final ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        assert licenses != null;
        map();
    }

    private void map() {
        map = licenses.stream().collect(Collectors.toMap(License::getLicenseId, Function.identity()));
    }

    // -----------------------------------------------------------------------------------------------------------------

    public String getLicenseListVersion() {
        return licenseListVersion;
    }

    // -------------------------------------------------------------------------------------------------------- licenses

    /**
     * Returns an unmodifiable map of license-ids and licenses.
     *
     * @return an unmodifiable map of license-ids and licenses.
     */
    public Map<String, License> getLicenses() {
        if (map == null) {
            map();
        }
        return map;
    }

    /**
     * Returns the license associated with specified license-id.
     *
     * @param licenseId the license-id.
     * @return the license associated with {@code licenseId}; {@code null} when no license matches.
     */
    public License getLicense(final String licenseId) {
        Objects.requireNonNull(licenseId, "licenseId is null");
        return getLicenses().get(licenseId);
    }

    // -----------------------------------------------------------------------------------------------------------------

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    // -----------------------------------------------------------------------------------------------------------------
    private String licenseListVersion;

    @ToString.Exclude
    private List<License> licenses;

    @Setter(AccessLevel.NONE)
    private LocalDate releaseDate;

    // -----------------------------------------------------------------------------------------------------------------
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private transient Map<String, License> map;
}
