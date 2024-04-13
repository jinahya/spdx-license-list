# SPDX License List

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
