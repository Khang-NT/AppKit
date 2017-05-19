package com.mstage.appkit.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.mstage.appkit.util.DataSnapshotWrapper;

/**
 * Created by Khang NT on 5/4/17.
 * Email: khang.neon.1997@gmail.com
 */

public class StatusBarConfig implements Parcelable {
    private boolean show;
    private String color;

    protected StatusBarConfig(Parcel in) {
        show = in.readByte() != 0;
        color = in.readString();
    }

    public static final Creator<StatusBarConfig> CREATOR = new Creator<StatusBarConfig>() {
        @Override
        public StatusBarConfig createFromParcel(Parcel in) {
            return new StatusBarConfig(in);
        }

        @Override
        public StatusBarConfig[] newArray(int size) {
            return new StatusBarConfig[size];
        }
    };

    public static StatusBarConfig from(DataSnapshotWrapper dataSnapshot) {
        if (dataSnapshot.hasChildren()) {
            return new StatusBarConfig(dataSnapshot.get("show", Boolean.class, true),
                    dataSnapshot.get("color", String.class, "#000000"));
        }
        return null;
    }

    public StatusBarConfig(boolean show, String color) {
        this.show = show;
        this.color = color;
    }

    public boolean isShow() {
        return show;
    }

    public String getColor() {
        return color;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (show ? 1 : 0));
        dest.writeString(color);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatusBarConfig that = (StatusBarConfig) o;

        if (show != that.show) return false;
        return color != null ? color.equals(that.color) : that.color == null;

    }

    @Override
    public int hashCode() {
        int result = (show ? 1 : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        return result;
    }
}
