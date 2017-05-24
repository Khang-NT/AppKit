package com.mstage.appkit.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.GenericTypeIndicator;
import com.mstage.appkit.AppKitApplication;
import com.mstage.appkit.util.DataSnapshotWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Khang NT on 5/10/17.
 * Email: khang.neon.1997@gmail.com
 */

public class HttpDataSourceConfig implements Parcelable {
    private String uri;
    private String method;
    private Map<String, String> headers;

    @Inject
    OkHttpClient mOkHttpClient;

    protected HttpDataSourceConfig(Parcel in) {
        uri = in.readString();
        method = in.readString();

        headers = new HashMap<>();
        int headerCount = in.readInt();
        for (int i = 0; i < headerCount; i++) {
            headers.put(in.readString(), in.readString());
        }
    }

    public static final Creator<HttpDataSourceConfig> CREATOR = new Creator<HttpDataSourceConfig>() {
        @Override
        public HttpDataSourceConfig createFromParcel(Parcel in) {
            return new HttpDataSourceConfig(in);
        }

        @Override
        public HttpDataSourceConfig[] newArray(int size) {
            return new HttpDataSourceConfig[size];
        }
    };

    public static HttpDataSourceConfig from(DataSnapshotWrapper dataSnapshot) {
        if (dataSnapshot.hasChildren()) {
            GenericTypeIndicator<Map<String, String>> mapGenericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {};
            return new HttpDataSourceConfig(dataSnapshot.get("uri", String.class),
                    dataSnapshot.get("method", String.class),
                    dataSnapshot.get("headers", mapGenericTypeIndicator));
        }
        throw new IllegalArgumentException("Invalid data source config: EMPTY");
    }

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

    public <T> Single<T> executeRequest(Context context, Function<String, T> responseTransformer) {
        AppKitApplication.getAppKitInjector(context).inject(this);
        return Single.<String>create(emitter -> {
            Request.Builder requestBuilder = new Request.Builder()
                    .url(getUri());
            if ("GET".equalsIgnoreCase(getMethod())) {
                requestBuilder.get();
            } else if ("POST".equalsIgnoreCase(getMethod())) {
                // unsupported POST for now
            }
            if (getHeaders() != null) {
                Set<Map.Entry<String, String>> entries = getHeaders().entrySet();
                for (Map.Entry<String, String> entry : entries) {
                    requestBuilder.header(entry.getKey(), entry.getValue());
                }
            }
            Response response = mOkHttpClient.newCall(requestBuilder.build()).execute();
            try {
                emitter.onSuccess(response.body().string());
            } finally {
                response.close();
            }
        }).map(responseTransformer).subscribeOn(Schedulers.io());
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uri);
        dest.writeString(method);
        if (headers == null) {
            dest.writeInt(0);
        } else {
            dest.writeInt(headers.size());
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                dest.writeString(entry.getKey());
                dest.writeString(entry.getValue());
            }
        }
    }
}
