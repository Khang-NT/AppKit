package com.mstage.appkit.model;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Parcel;
import android.os.Parcelable;

import com.mstage.appkit.util.DataSnapshotWrapper;

/**
 * Created by Khang NT on 5/4/17.
 * Email: khang.neon.1997@gmail.com
 */

public class Background implements Parcelable {
    public static final String TYPE_COLOR = "color";
    private static final String TYPE_GRADIENT = "gradient";
    private String type;
    private String value;

    private Background(Parcel in) {
        type = in.readString();
        value = in.readString();
    }

    public static final Creator<Background> CREATOR = new Creator<Background>() {
        @Override
        public Background createFromParcel(Parcel in) {
            return new Background(in);
        }

        @Override
        public Background[] newArray(int size) {
            return new Background[size];
        }
    };

    public static Background from(DataSnapshotWrapper dataSnapshot) {
        if (dataSnapshot.hasChildren()) {
            return new Background(dataSnapshot.get("type", String.class),
                    dataSnapshot.get("value", String.class));
        }
        return null;
    }

    public Background(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public Drawable asDrawable() {
        if (TYPE_COLOR.equals(getType())) {
            int color = Color.parseColor(getValue());
            return new ColorDrawable(color);
        } else if (TYPE_GRADIENT.equals(getType())) {
            String[] colorValues = getValue().split(",");
            int[] colors = new int[colorValues.length];
            for (int i = 0; i < colorValues.length; i++) colors[i] = Color.parseColor(colorValues[i]);
            return new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    colors);
        }
//        throw new IllegalArgumentException("Unsupported type: " + getType());
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Background that = (Background) o;

        if (!type.equals(that.type)) return false;
        return value != null ? value.equals(that.value) : that.value == null;

    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
