package com.github.jinahya.spdx.license.data.bind.json;

import java.net.URL;
import java.util.List;

public class LicenseDetail
        extends License {

    private String reference;

    private Boolean deprecatedLicenseId;

    private String detailsUrl;

    private Integer referenceNumber;

    private String name;

    private String licenseId;

    private List<URL> seeAlso;

    private Boolean osiApproved;
}
