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
public final class Licenses
        implements Serializable {

    private static final long serialVersionUID = 1668175366953366612L;

    // -----------------------------------------------------------------------------------------------------------------
    static final String NAME = "licenses.bin";

    // -----------------------------------------------------------------------------------------------------------------
    private static final class InstanceHolder {

        private static final Licenses INSTANCE;

        static {
            final var resource = Licenses.class.getResource(NAME);
            assert resource != null;
            try {
                INSTANCE = ObjectIoUtils.read(new File(resource.toURI()));
            } catch (final Exception e) {
                throw new InstantiationError("failed to load resource for `" + NAME + "'");
            }
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    public static Licenses getInstance() {
        return InstanceHolder.INSTANCE;
    }

    // -----------------------------------------------------------------------------------------------------------------
    // https://stackoverflow.com/a/3343314/330457
    private void readObject(final ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        assert licenses != null;
        map();
    }

    // -------------------------------------------------------------------------------------------------------- licenses
    private void map() {
        map = licenses.stream().collect(Collectors.toMap(License::getLicenseId, Function.identity()));
    }

    /**
     * Returns an unmodifiable list of loaded licenses.
     *
     * @return an unmodifiable list of loaded licenses.
     */
    public Map<String, License> getLicenses() {
        if (map == null) {
            map();
        }
        return map;
    }

    /**
     * Returns the license associated with specified license id.
     *
     * @param id the license id.
     * @return the license associated with {@code id}; {@code null} when no license matches.
     */
    public License getLicense(final String id) {
        Objects.requireNonNull(id, "id is null");
        return getLicenses().get(id);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Setter(AccessLevel.NONE)
    @Getter
    private String licenseListVersion;

    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private List<License> licenses;

    @Setter(AccessLevel.NONE)
    @Getter
    private LocalDate releaseDate;

    // -----------------------------------------------------------------------------------------------------------------
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private transient Map<String, License> map;
}
