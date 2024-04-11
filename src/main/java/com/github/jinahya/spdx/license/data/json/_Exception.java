package com.github.jinahya.spdx.license.data.json;

import lombok.*;

import java.io.Serializable;
import java.net.URL;
import java.util.List;

@Getter
@EqualsAndHashCode
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class _Exception
        implements Serializable {

    private static final long serialVersionUID = 8502499247921432315L;

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
