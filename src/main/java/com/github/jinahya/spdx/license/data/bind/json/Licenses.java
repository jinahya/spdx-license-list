package com.github.jinahya.spdx.license.data.bind.json;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Setter
@Getter
@EqualsAndHashCode
@ToString
public class Licenses
        implements Serializable {

    private static final long serialVersionUID = 1668175366953366612L;

    // ----------------------------------------------------------------------------------------------------------- licenses

    public List<License> getLicenses() {
        return Optional.ofNullable(licenses)
                .map(Collections::unmodifiableList)
                .orElse(null);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @NotEmpty
    private String licenseListVersion;

    @NotNull
    @ToString.Exclude
    private @NotEmpty List<@Valid @NotNull License> licenses;

    @PastOrPresent
    private LocalDate releaseDate;
}
