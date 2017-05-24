package com.mstage.appkit.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.mstage.appkit.util.DataSnapshotWrapper;

/**
 * Created by Khang NT on 5/9/17.
 * Email: khang.neon.1997@gmail.com
 */

public class MainScreenConfig implements Parcelable {
    private StatusBarConfig statusBarConfig;
    private TopBarConfig topBarConfig;
    private Background background;
    private NavigationViewConfig navigationViewConfig;

    protected MainScreenConfig(Parcel in) {
        statusBarConfig = in.readParcelable(StatusBarConfig.class.getClassLoader());
        topBarConfig = in.readParcelable(TopBarConfig.class.getClassLoader());
        background = in.readParcelable(Background.class.getClassLoader());
        navigationViewConfig = in.readParcelable(NavigationViewConfig.class.getClassLoader());
    }

    public static final Creator<MainScreenConfig> CREATOR = new Creator<MainScreenConfig>() {
        @Override
        public MainScreenConfig createFromParcel(Parcel in) {
            return new MainScreenConfig(in);
        }

        @Override
        public MainScreenConfig[] newArray(int size) {
            return new MainScreenConfig[size];
        }
    };

    public static MainScreenConfig from(DataSnapshotWrapper dataSnapshot) {
        return new MainScreenConfig(StatusBarConfig.from(dataSnapshot.child("status_bar")),
                TopBarConfig.from(dataSnapshot.child("top_bar")),
                Background.from(dataSnapshot.child("background")),
                NavigationViewConfig.from(dataSnapshot.child("navigation_view")));
    }

    public MainScreenConfig(StatusBarConfig statusBarConfig, TopBarConfig topBarConfig,
                            Background background, NavigationViewConfig navigationViewConfig) {
        this.statusBarConfig = statusBarConfig;
        this.topBarConfig = topBarConfig;
        this.background = background;
        this.navigationViewConfig = navigationViewConfig;
    }

    public StatusBarConfig getStatusBarConfig() {
        return statusBarConfig;
    }

    public TopBarConfig getTopBarConfig() {
        return topBarConfig;
    }

    public Background getBackground() {
        return background;
    }

    public NavigationViewConfig getNavigationViewConfig() {
        return navigationViewConfig;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(statusBarConfig, flags);
        dest.writeParcelable(topBarConfig, flags);
        dest.writeParcelable(background, flags);
        dest.writeParcelable(navigationViewConfig, flags);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MainScreenConfig that = (MainScreenConfig) o;

        if (statusBarConfig != null ? !statusBarConfig.equals(that.statusBarConfig) : that.statusBarConfig != null)
            return false;
        if (topBarConfig != null ? !topBarConfig.equals(that.topBarConfig) : that.topBarConfig != null)
            return false;
        if (background != null ? !background.equals(that.background) : that.background != null)
            return false;
        return navigationViewConfig != null ? navigationViewConfig.equals(that.navigationViewConfig) : that.navigationViewConfig == null;

    }

    @Override
    public int hashCode() {
        int result = statusBarConfig != null ? statusBarConfig.hashCode() : 0;
        result = 31 * result + (topBarConfig != null ? topBarConfig.hashCode() : 0);
        result = 31 * result + (background != null ? background.hashCode() : 0);
        result = 31 * result + (navigationViewConfig != null ? navigationViewConfig.hashCode() : 0);
        return result;
    }
}
