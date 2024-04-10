package com.github.jinahya.spdx.license.data.bind.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.net.URL;
import java.time.OffsetDateTime;
import java.util.List;

@Setter
@Getter
@EqualsAndHashCode
@ToString
public class License
        implements Serializable {

    private static final long serialVersionUID = 1453593338100194658L;

    // -----------------------------------------------------------------------------------------------------------------
    @Setter
    @Getter
    @EqualsAndHashCode
    @ToString
    public static class CrossRef {

        private String match;

        private String url;

        @JsonbProperty("isValid")
        @JsonProperty("isValid")
        private Boolean valid;

        @JsonbProperty("isLive")
        @JsonProperty("isLive")
        private Boolean live;

        private OffsetDateTime timestamp;

        @JsonbProperty("isWayBackLink")
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
    @JsonbProperty("isDeprecatedLicenseId")
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
    @JsonbProperty("isOsiApproved")
    @JsonProperty("isOsiApproved")
    private Boolean osiApproved;

    // -----------------------------------------------------------------------------------------------------------------
    private String licenseText;

    @JsonbProperty("crossRef")
    @JsonProperty("crossRef")
    private List<CrossRef> crossRefs;

    private String licenseTextHtml;
}
