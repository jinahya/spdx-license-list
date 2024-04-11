package com.github.jinahya.spdx.license.data.json;

import lombok.*;

import java.io.Serializable;
import java.net.URL;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@EqualsAndHashCode
@ToString(callSuper = true)
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

        private Boolean isValid;

        private Boolean isLive;

        private OffsetDateTime timestamp;

        private Boolean isWayBackLink;

        private Integer order;
    }

    // -----------------------------------------------------------------------------------------------------------------
    private String reference;

    private Boolean isDeprecatedLicenseId;

    private String detailsUrl;

    private Integer referenceNumber;

    private String name;

    private String licenseId;

    private List<URL> seeAlso;

    private Boolean isOsiApproved;

    // -----------------------------------------------------------------------------------------------------------------
    private String licenseText;

    private List<CrossRef> crossRef;

    private String licenseTextHtml;

    // -----------------------------------------------------------------------------------------------------------------
    private Boolean isFsfLibre;

    private String standardLicenseTemplate;

    private String standardLicenseHeaderTemplate;

    private String standardLicenseHeader;

    private String standardLicenseHeaderHtml;

    private String licenseComments;

    private String deprecatedVersion;
}
