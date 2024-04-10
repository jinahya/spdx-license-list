package com.github.jinahya.spdx.license.data.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.net.URL;
import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class _Exception
        implements Serializable {

    private static final long serialVersionUID = 8502499247921432315L;

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
    private String licenseExceptionId;

    private List<@NotNull URL> seeAlso;

    // -----------------------------------------------------------------------------------------------------------------
    private String licenseExceptionText;

    private String licenseComments;

    private String licenseExceptionTemplate;

    private String exceptionTextHtml;

    // -----------------------------------------------------------------------------------------------------------------
    private String deprecatedVersion;
}
