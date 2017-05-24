package com.mstage.appkit.section;

import android.os.Parcel;
import android.os.Parcelable;

import com.mstage.appkit.model.Background;
import com.mstage.appkit.model.FontConfig;
import com.mstage.appkit.util.DataSnapshotWrapper;

/**
 * Created by Khang NT on 5/23/17.
 * Email: khang.neon.1997@gmail.com
 */

public class Section0ItemConfig implements Parcelable {
    private Background background;
    private FontConfig font;
    private String space;
    private String placeHolder;

    public static Section0ItemConfig from(DataSnapshotWrapper dataSnapshot) {
        return new Section0ItemConfig(Background.from(dataSnapshot.child("background")),
                FontConfig.from(dataSnapshot.child("font")),
                dataSnapshot.get("space", String.class),
                dataSnapshot.get("thumbnail_place_holder", String.class));
    }

    public Section0ItemConfig(Background background, FontConfig font, String space, String placeHolder) {
        this.background = background;
        this.font = font;
        this.space = space;
        this.placeHolder = placeHolder;
    }

    public Background getBackground() {
        return background;
    }

    public FontConfig getFont() {
        return font;
    }

    public String getSpace() {
        return space;
    }

    public String getPlaceHolder() {
        return placeHolder;
    }

    protected Section0ItemConfig(Parcel in) {
        background = in.readParcelable(Background.class.getClassLoader());
        font = in.readParcelable(FontConfig.class.getClassLoader());
        space = in.readString();
        placeHolder = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(background, flags);
        dest.writeParcelable(font, flags);
        dest.writeString(space);
        dest.writeString(placeHolder);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Section0ItemConfig> CREATOR = new Creator<Section0ItemConfig>() {
        @Override
        public Section0ItemConfig createFromParcel(Parcel in) {
            return new Section0ItemConfig(in);
        }

        @Override
        public Section0ItemConfig[] newArray(int size) {
            return new Section0ItemConfig[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Section0ItemConfig that = (Section0ItemConfig) o;

        if (background != null ? !background.equals(that.background) : that.background != null)
            return false;
        if (font != null ? !font.equals(that.font) : that.font != null) return false;
        if (space != null ? !space.equals(that.space) : that.space != null) return false;
        return placeHolder != null ? placeHolder.equals(that.placeHolder) : that.placeHolder == null;

    }

    @Override
    public int hashCode() {
        int result = background != null ? background.hashCode() : 0;
        result = 31 * result + (font != null ? font.hashCode() : 0);
        result = 31 * result + (space != null ? space.hashCode() : 0);
        result = 31 * result + (placeHolder != null ? placeHolder.hashCode() : 0);
        return result;
    }
}
