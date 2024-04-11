package com.github.jinahya.spdx.license.data.json;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@EqualsAndHashCode
@ToString(callSuper = true)
public class _Exception
        implements Serializable {

    private static final long serialVersionUID = 8502499247921432315L;

    // -----------------------------------------------------------------------------------------------------------------
    private _Exception() {
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

    public String getLicenseExceptionId() {
        return licenseExceptionId;
    }

    public List<URL> getSeeAlso() {
        return Optional.ofNullable(seeAlso)
                .map(Collections::unmodifiableList)
                .orElse(null);
    }

    public String getLicenseExceptionText() {
        return licenseExceptionText;
    }

    public String getLicenseComments() {
        return licenseComments;
    }

    public String getLicenseExceptionTemplate() {
        return licenseExceptionTemplate;
    }

    public String getExceptionTextHtml() {
        return exceptionTextHtml;
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

    private String licenseExceptionId;

    private List<URL> seeAlso;

    // -----------------------------------------------------------------------------------------------------------------
    private String licenseExceptionText;

    private String licenseComments;

    private String licenseExceptionTemplate;

    private String exceptionTextHtml;

    // -----------------------------------------------------------------------------------------------------------------
    private String deprecatedVersion;
}
