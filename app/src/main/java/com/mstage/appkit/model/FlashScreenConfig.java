package com.mstage.appkit.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.GenericTypeIndicator;
import com.mstage.appkit.util.DataSnapshotWrapper;

import java.util.List;

/**
 * Created by Khang NT on 5/5/17.
 * Email: khang.neon.1997@gmail.com
 */

public class FlashScreenConfig implements Parcelable {
    private StatusBarConfig statusBarConfig;
    private Background background;
    private String logo;
    private List<String> preloadImages;

    protected FlashScreenConfig(Parcel in) {
        statusBarConfig = in.readParcelable(StatusBarConfig.class.getClassLoader());
        background = in.readParcelable(Background.class.getClassLoader());
        logo = in.readString();
        preloadImages = in.createStringArrayList();
    }

    public static final Creator<FlashScreenConfig> CREATOR = new Creator<FlashScreenConfig>() {
        @Override
        public FlashScreenConfig createFromParcel(Parcel in) {
            return new FlashScreenConfig(in);
        }

        @Override
        public FlashScreenConfig[] newArray(int size) {
            return new FlashScreenConfig[size];
        }
    };

    public static FlashScreenConfig from(DataSnapshotWrapper dataSnapshot) {
        GenericTypeIndicator<List<String>> genericTypeIndicator = new GenericTypeIndicator<List<String>>() {};
        return new FlashScreenConfig(StatusBarConfig.from(dataSnapshot.child("status_bar")),
                Background.from(dataSnapshot.child("background")),
                dataSnapshot.get("logo", String.class),
                dataSnapshot.get("pre_load_image", genericTypeIndicator));
    }

    public FlashScreenConfig(StatusBarConfig statusBarConfig, Background background,
                             String logo, List<String> preloadImages) {
        this.statusBarConfig = statusBarConfig;
        this.background = background;
        this.logo = logo;
        this.preloadImages = preloadImages;
    }

    public StatusBarConfig getStatusBarConfig() {
        return statusBarConfig;
    }

    public Background getBackground() {
        return background;
    }

    public String getLogo() {
        return logo;
    }

    public List<String> getPreloadImages() {
        return preloadImages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(statusBarConfig, flags);
        dest.writeParcelable(background, flags);
        dest.writeString(logo);
        dest.writeStringList(preloadImages);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FlashScreenConfig that = (FlashScreenConfig) o;

        if (statusBarConfig != null ? !statusBarConfig.equals(that.statusBarConfig) : that.statusBarConfig != null)
            return false;
        if (background != null ? !background.equals(that.background) : that.background != null)
            return false;
        if (logo != null ? !logo.equals(that.logo) : that.logo != null) return false;
        return preloadImages != null ? preloadImages.equals(that.preloadImages) : that.preloadImages == null;

    }

    @Override
    public int hashCode() {
        int result = statusBarConfig != null ? statusBarConfig.hashCode() : 0;
        result = 31 * result + (background != null ? background.hashCode() : 0);
        result = 31 * result + (logo != null ? logo.hashCode() : 0);
        result = 31 * result + (preloadImages != null ? preloadImages.hashCode() : 0);
        return result;
    }
}
