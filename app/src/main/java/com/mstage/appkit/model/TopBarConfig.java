package com.mstage.appkit.model;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.mstage.appkit.util.DataSnapshotWrapper;

import timber.log.Timber;

/**
 * Created by Khang NT on 5/4/17.
 * Email: khang.neon.1997@gmail.com
 */

public class TopBarConfig implements Parcelable {
    private Background background;
    private FontConfig font;
    private String contentAlign;
    private String defaultTitle;
    private String defaultLogo;
    private String iconColor;

    protected TopBarConfig(Parcel in) {
        background = in.readParcelable(Background.class.getClassLoader());
        font = in.readParcelable(FontConfig.class.getClassLoader());
        contentAlign = in.readString();
        defaultTitle = in.readString();
        defaultLogo = in.readString();
        iconColor = in.readString();
    }

    public static final Creator<TopBarConfig> CREATOR = new Creator<TopBarConfig>() {
        @Override
        public TopBarConfig createFromParcel(Parcel in) {
            return new TopBarConfig(in);
        }

        @Override
        public TopBarConfig[] newArray(int size) {
            return new TopBarConfig[size];
        }
    };

    public static TopBarConfig from(DataSnapshotWrapper dataSnapshot) {
        if (!dataSnapshot.hasChildren()) return null;
        return new TopBarConfig(Background.from(dataSnapshot.child("background")),
                FontConfig.from(dataSnapshot.child("font")),
                dataSnapshot.get("content_align", String.class),
                dataSnapshot.get("default_title", String.class),
                dataSnapshot.get("default_logo", String.class),
                dataSnapshot.get("icon_color", String.class));
    }

    public TopBarConfig(Background background, FontConfig font, String contentAlign, String defaultTitle,
                        String defaultLogo, String iconColor) {
        this.background = background;
        this.font = font;
        this.contentAlign = contentAlign;
        this.defaultTitle = defaultTitle;
        this.defaultLogo = defaultLogo;
        this.iconColor = iconColor;
    }

    public Background getBackground() {
        return background;
    }

    public FontConfig getFont() {
        return font;
    }

    public String getContentAlign() {
        return contentAlign;
    }

    public String getDefaultTitle() {
        return defaultTitle;
    }

    public String getDefaultLogo() {
        return defaultLogo;
    }

    public int getIconColor(int defaultColor) {
        if (!TextUtils.isEmpty(iconColor)) {
            try {
                return Color.parseColor(iconColor);
            } catch (Throwable ex) {
                Timber.d(ex, "Can't parse icon color: %s, return default color: " + defaultColor);
            }
        }
        return defaultColor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(background, flags);
        dest.writeParcelable(font, flags);
        dest.writeString(contentAlign);
        dest.writeString(defaultTitle);
        dest.writeString(defaultLogo);
        dest.writeString(iconColor);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TopBarConfig that = (TopBarConfig) o;

        if (background != null ? !background.equals(that.background) : that.background != null)
            return false;
        if (font != null ? !font.equals(that.font) : that.font != null) return false;
        if (contentAlign != null ? !contentAlign.equals(that.contentAlign) : that.contentAlign != null)
            return false;
        if (defaultTitle != null ? !defaultTitle.equals(that.defaultTitle) : that.defaultTitle != null)
            return false;
        return defaultLogo != null ? defaultLogo.equals(that.defaultLogo) : that.defaultLogo == null;

    }

    @Override
    public int hashCode() {
        int result = background != null ? background.hashCode() : 0;
        result = 31 * result + (font != null ? font.hashCode() : 0);
        result = 31 * result + (contentAlign != null ? contentAlign.hashCode() : 0);
        result = 31 * result + (defaultTitle != null ? defaultTitle.hashCode() : 0);
        result = 31 * result + (defaultLogo != null ? defaultLogo.hashCode() : 0);
        return result;
    }
}
