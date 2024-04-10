package com.github.jinahya.spdx.license.data.bind.json;

import java.io.Serializable;
import java.net.URL;
import java.util.List;

public class _License
        implements Serializable {

    private String reference;

    private Boolean deprecatedLicenseId;

    private String detailsUrl;

    private Integer referenceNumber;

    private String name;

    private String licenseId;

    private List<URL> seeAlso;

    private Boolean osiApproved;
}
