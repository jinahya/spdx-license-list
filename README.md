# SPDX License List

Bound instances of [SPDX License List Data](https://github.com/spdx/license-list-data).

## JSON

```java
final  var instance = Licenses.getInstance();
final  var licenses = instance.getLicenses();
final  var license  = instance.getLicense("0BSD");
assert Objects.equals(license.getLicenseId(), "0BSD");
```
