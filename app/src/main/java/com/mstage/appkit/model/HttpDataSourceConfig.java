package com.mstage.appkit.model;

import java.util.Map;

/**
 * Created by Khang NT on 5/10/17.
 * Email: khang.neon.1997@gmail.com
 */

public class HttpDataSourceConfig {
    private String uri;
    private String method;
    private Map<String, String> headers;

    public HttpDataSourceConfig(String uri, String method, Map<String, String> headers) {
        this.uri = uri;
        this.method = method;
        this.headers = headers;
    }

    public String getUri() {
        return uri;
    }

    public String getMethod() {
        return method;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HttpDataSourceConfig that = (HttpDataSourceConfig) o;

        if (uri != null ? !uri.equals(that.uri) : that.uri != null) return false;
        if (method != null ? !method.equals(that.method) : that.method != null) return false;
        return headers != null ? headers.equals(that.headers) : that.headers == null;

    }

    @Override
    public int hashCode() {
        int result = uri != null ? uri.hashCode() : 0;
        result = 31 * result + (method != null ? method.hashCode() : 0);
        result = 31 * result + (headers != null ? headers.hashCode() : 0);
        return result;
    }
}
