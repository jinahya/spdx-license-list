module com.github.jinahya.spdx.license.list {
    requires jakarta.xml.bind;
    requires static lombok;
    requires jakarta.validation;
    requires jakarta.json.bind;
    requires com.fasterxml.jackson.annotation;
//    requires jakarta.activation;
    exports com.github.jinahya.spdx.license;
    exports com.github.jinahya.spdx.license.bind;
}
