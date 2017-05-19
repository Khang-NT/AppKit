package com.mstage.appkit.model;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;

import com.mstage.appkit.util.DataSnapshotWrapper;

/**
 * Created by Khang NT on 5/4/17.
 * Email: khang.neon.1997@gmail.com
 */

public class Font implements Parcelable {
    private String fontFamily;
    private String fontSize;
    private String fontColor;

    protected Font(Parcel in) {
        fontFamily = in.readString();
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
                    dataSnapshot.get("font_size", String.class),
                    dataSnapshot.get("font_color", String.class));
        }
        return null;
    }

    public Font(String fontFamily, String fontSize, String fontColor) {
        this.fontFamily = fontFamily;
        this.fontSize = fontSize;
        this.fontColor = fontColor;
    }

    public String getFontFamily() {
        return fontFamily;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fontFamily);
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
