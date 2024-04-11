package com.github.jinahya.spdx.license.data.json;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.net.URL;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@EqualsAndHashCode
@ToString(callSuper = true)
public class License
        implements Serializable {

    private static final long serialVersionUID = 1453593338100194658L;

    // -----------------------------------------------------------------------------------------------------------------
    @EqualsAndHashCode
    @ToString
    public static class CrossRef
            implements Serializable {

        private static final long serialVersionUID = -9145988730071357050L;

        // -------------------------------------------------------------------------------------------------------------
        private CrossRef() {
            super();
        }

        // -------------------------------------------------------------------------------------------------------------

        public String getMatch() {
            return match;
        }

        public String getUrl() {
            return url;
        }

        public Boolean getValid() {
            return isValid;
        }

        public Boolean getLive() {
            return isLive;
        }

        public OffsetDateTime getTimestamp() {
            return timestamp;
        }

        public Boolean getWayBackLink() {
            return isWayBackLink;
        }

        public Integer getOrder() {
            return order;
        }

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
    private License() {
        super();
    }

    // -----------------------------------------------------------------------------------------------------------------

    public String getReference() {
        return reference;
    }

    public Boolean getDeprecatedLicenseId() {
        return isDeprecatedLicenseId;
    }

    public String getDetailsUrl() {
        return detailsUrl;
    }

    public Integer getReferenceNumber() {
        return referenceNumber;
    }

    public String getName() {
        return name;
    }

    public String getLicenseId() {
        return licenseId;
    }

    public List<URL> getSeeAlso() {
        return Optional.ofNullable(seeAlso)
                .map(Collections::unmodifiableList)
                .orElse(null);
    }

    public Boolean getOsiApproved() {
        return isOsiApproved;
    }

    public String getLicenseText() {
        return licenseText;
    }

    public List<CrossRef> getCrossRef() {
        return Optional.ofNullable(crossRef)
                .map(Collections::unmodifiableList)
                .orElse(null);
    }

    public String getLicenseTextHtml() {
        return licenseTextHtml;
    }

    public Boolean getFsfLibre() {
        return isFsfLibre;
    }

    public String getStandardLicenseTemplate() {
        return standardLicenseTemplate;
    }

    public String getStandardLicenseHeaderTemplate() {
        return standardLicenseHeaderTemplate;
    }

    public String getStandardLicenseHeader() {
        return standardLicenseHeader;
    }

    public String getStandardLicenseHeaderHtml() {
        return standardLicenseHeaderHtml;
    }

    public String getLicenseComments() {
        return licenseComments;
    }

    public String getDeprecatedVersion() {
        return deprecatedVersion;
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
