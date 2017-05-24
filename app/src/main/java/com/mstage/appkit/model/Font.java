package com.mstage.appkit.model;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.mstage.appkit.AppKitApplication;
import com.mstage.appkit.util.DataSnapshotWrapper;
import com.mstage.appkit.util.Preconditions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

/**
 * Created by Khang NT on 5/4/17.
 * Email: khang.neon.1997@gmail.com
 */

public class Font implements Parcelable {
    private String fontFamily;
    private String fontUrl;
    private String fontSize;
    private String fontColor;

    @Inject
    OkHttpClient mOkHttpClient;

    protected Font(Parcel in) {
        fontFamily = in.readString();
        fontUrl = in.readString();
        fontSize = in.readString();
        fontColor = in.readString();
    }

    public static final Creator<Font> CREATOR = new Creator<Font>() {
        @Override
        public Font createFromParcel(Parcel in) {
            return new Font(in);
        }

        @Override
        public Font[] newArray(int size) {
            return new Font[size];
        }
    };

    public static Font from(DataSnapshotWrapper dataSnapshot) {
        if (dataSnapshot.hasChildren()) {
            return new Font(dataSnapshot.get("font_family", String.class),
                    dataSnapshot.get("font_url", String.class),
                    dataSnapshot.get("font_size", String.class),
                    dataSnapshot.get("font_color", String.class));
        }
        return null;
    }

    public Font(String fontFamily, String fontUrl, String fontSize, String fontColor) {
        this.fontFamily = fontFamily;
        this.fontUrl = fontUrl;
        this.fontSize = fontSize;
        this.fontColor = fontColor;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public String getFontUrl() {
        return fontUrl;
    }

    public String getFontSize() {
        return fontSize;
    }

    public String getFontColor() {
        return fontColor;
    }

    public Typeface getTypeface(Context context) {
        AssetManager am = context.getApplicationContext().getAssets();

        return Typeface.createFromAsset(am, "font/" + getFontFamily() + ".ttf");
    }

    public void getTypeFaceOnline(Context context, Consumer<Typeface> onSuccess) {
        AppKitApplication.getAppKitInjector(context).inject(this);
        String url = Preconditions.checkNotNull(getFontUrl());
        File fontFile = new File(context.getCacheDir(), "font" + url.hashCode());
        if (fontFile.exists()) {
            try {
                onSuccess.accept(Typeface.createFromFile(fontFile));
            } catch (Exception e) {
                Timber.e(e, "Apply font failed");
            }
        } else {
            Request request = new Request.Builder()
                    .url(url)
                    .cacheControl(new CacheControl.Builder().maxAge(24, TimeUnit.HOURS).build())
                    .build();
            mOkHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, IOException e) {
                    Timber.e(e, "Download font failed");
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    try {
                        OutputStream os = new FileOutputStream(fontFile);
                        os.write(response.body().bytes());
                        os.flush();
                        os.close();
                        onSuccess.accept(Typeface.createFromFile(fontFile));
                    } catch (Exception e) {
                        Timber.e(e, "Apply font failed");
                    } finally {
                        response.close();
                    }
                }
            });
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fontFamily);
        dest.writeString(fontUrl);
        dest.writeString(fontSize);
        dest.writeString(fontColor);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Font font = (Font) o;

        if (fontFamily != null ? !fontFamily.equals(font.fontFamily) : font.fontFamily != null)
            return false;
        if (fontSize != null ? !fontSize.equals(font.fontSize) : font.fontSize != null)
            return false;
        return fontColor != null ? fontColor.equals(font.fontColor) : font.fontColor == null;

    }

    @Override
    public int hashCode() {
        int result = fontFamily != null ? fontFamily.hashCode() : 0;
        result = 31 * result + (fontSize != null ? fontSize.hashCode() : 0);
        result = 31 * result + (fontColor != null ? fontColor.hashCode() : 0);
        return result;
    }
}
