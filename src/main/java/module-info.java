/**
 * Provides packages bound from <a href="https://github.com/spdx/license-list-data">SPDX License List Data</a>.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
module com.github.jinahya.spdx.licenses {
    requires static lombok;
    exports com.github.jinahya.spdx.license.data.json;
}
