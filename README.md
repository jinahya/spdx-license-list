# SPDX License List

[![Java CI with Maven](https://github.com/jinahya/spdx-licenses/actions/workflows/maven.yml/badge.svg)](https://github.com/jinahya/spdx-licenses/actions/workflows/maven.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=jinahya_spdx-licenses&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=jinahya_spdx-licenses)
![Maven Central Version](https://img.shields.io/maven-central/v/com.github.jinahya/spdx-licenses)
[![javadoc](https://javadoc.io/badge2/com.github.jinahya/spdx-licenses/javadoc.svg)](https://javadoc.io/doc/com.github.jinahya/spdx-licenses)

Bound instances of [SPDX License List Data](https://github.com/spdx/license-list-data).

## JSON

```java
final var instance = Licenses.getInstance();

final var licenseId = "0BSD";
final var license = instance.getLicense(licenseId, true);
assert license.getLicenseId().equals(licenseId);
```
```java
final var instance = _Exceptions.getInstance();

final var licenseExceptionId = "389-exception";
final var exception = instance.getException(licenseExceptionId, true);
assert exception.getLicenseExceptionId().equals(licenseExceptionId);
```
