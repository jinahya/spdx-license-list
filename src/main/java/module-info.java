module com.github.jinahya.spdx.license.list {
    requires static lombok;
    requires jakarta.validation;
    requires com.fasterxml.jackson.annotation;
    exports com.github.jinahya.spdx.license.data.json;
}
