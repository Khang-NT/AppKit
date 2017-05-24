package com.mstage.appkit.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.mstage.appkit.util.DataSnapshotWrapper;

/**
 * Created by Khang NT on 5/24/17.
 * Email: khang.neon.1997@gmail.com
 */

public class NavigationViewConfig implements Parcelable {
    private Background background;
    private FontConfig fontConfig;

    protected NavigationViewConfig(Parcel in) {
        background = in.readParcelable(Background.class.getClassLoader());
        fontConfig = in.readParcelable(FontConfig.class.getClassLoader());
    }

    public static final Creator<NavigationViewConfig> CREATOR = new Creator<NavigationViewConfig>() {
        @Override
        public NavigationViewConfig createFromParcel(Parcel in) {
            return new NavigationViewConfig(in);
        }

        @Override
        public NavigationViewConfig[] newArray(int size) {
            return new NavigationViewConfig[size];
        }
    };

    public static NavigationViewConfig from(DataSnapshotWrapper dataSnapshot) {
        return new NavigationViewConfig(Background.from(dataSnapshot.child("background")),
                FontConfig.from(dataSnapshot.child("font")));
    }

    public NavigationViewConfig(Background background, FontConfig fontConfig) {
        this.background = background;
        this.fontConfig = fontConfig;
    }

    public Background getBackground() {
        return background;
    }

    public FontConfig getFontConfig() {
        return fontConfig;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NavigationViewConfig that = (NavigationViewConfig) o;

        if (background != null ? !background.equals(that.background) : that.background != null)
            return false;
        return fontConfig != null ? fontConfig.equals(that.fontConfig) : that.fontConfig == null;

    }

    @Override
    public int hashCode() {
        int result = background != null ? background.hashCode() : 0;
        result = 31 * result + (fontConfig != null ? fontConfig.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(background, flags);
        dest.writeParcelable(fontConfig, flags);
    }
}
