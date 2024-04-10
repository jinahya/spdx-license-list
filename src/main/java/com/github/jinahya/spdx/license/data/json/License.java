package com.github.jinahya.spdx.license.data.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.io.Serializable;
import java.net.URL;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class License
        implements Serializable {

    private static final long serialVersionUID = 1453593338100194658L;

    // -----------------------------------------------------------------------------------------------------------------
    @Getter
    @EqualsAndHashCode
    @ToString
    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    public static class CrossRef
            implements Serializable {

        private static final long serialVersionUID = -9145988730071357050L;

        // -----------------------------------------------------------------------------------------------------------------
        // -------------------------------------------------------------------------------------------------------------
        private String match;

        private String url;

        @JsonProperty("isValid")
        private Boolean valid;

        @JsonProperty("isLive")
        private Boolean live;

        private OffsetDateTime timestamp;

        @JsonProperty("isWayBackLink")
        private Boolean wayBackLink;

        @PositiveOrZero
        @NotNull
        private Integer order;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @NotBlank
    private String reference;

    @NotNull
    @JsonProperty("isDeprecatedLicenseId")
    private Boolean deprecatedLicenseId;

    @NotBlank
    private String detailsUrl;

    @NotNull
    private Integer referenceNumber;

    @NotBlank
    private String name;

    @NotBlank
    private String licenseId;

    private List<@NotNull URL> seeAlso;

    @NotNull
    @JsonProperty("isOsiApproved")
    private Boolean osiApproved;

    // -----------------------------------------------------------------------------------------------------------------
    private String licenseText;

    @JsonProperty("crossRef")
    private List<CrossRef> crossRefs;

    private String licenseTextHtml;

    // -----------------------------------------------------------------------------------------------------------------
    @JsonProperty("isFsfLibre")
    private Boolean fsfLibre;

    private String standardLicenseTemplate;

    private String standardLicenseHeaderTemplate;

    private String standardLicenseHeader;

    private String standardLicenseHeaderHtml;

    private String licenseComments;

    private String deprecatedVersion;
}
