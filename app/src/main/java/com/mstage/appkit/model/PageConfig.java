package com.mstage.appkit.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.mstage.appkit.section.SectionConfig;
import com.mstage.appkit.section.SectionMap;
import com.mstage.appkit.util.DataSnapshotWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khang NT on 5/22/17.
 * Email: khang.neon.1997@gmail.com
 *
 * Because SectionConfig can't be parcelable, PageConfig can't implement Parcelable.
 *
 */
public class PageConfig implements Parcelable {
    private String title;
    private List<SectionConfig> sectionConfigList;

    protected PageConfig(Parcel in) {
        title = in.readString();
        sectionConfigList = new ArrayList<>();
        int sectionCount = in.readInt();
        for (int i = 0; i < sectionCount; i++) {
            sectionConfigList.add(SectionConfig.createFromParcel(in, SectionMap.getInstance()));
        }
    }

    public static final Creator<PageConfig> CREATOR = new Creator<PageConfig>() {
        @Override
        public PageConfig createFromParcel(Parcel in) {
            return new PageConfig(in);
        }

        @Override
        public PageConfig[] newArray(int size) {
            return new PageConfig[size];
        }
    };

    public static PageConfig from(DataSnapshotWrapper dataSnapshot) {
        List<SectionConfig> sectionConfigList = new ArrayList<>();
        DataSnapshotWrapper sections = dataSnapshot.child("sections");
        SectionMap sectionMap = SectionMap.getInstance();
        for (DataSnapshotWrapper dataSnapshotWrapper : sections.getChildren()) {
            sectionConfigList.add(sectionMap.getSectionConfigFromDataSnapshot(dataSnapshotWrapper));
        }
        return new PageConfig(dataSnapshot.child("display").get("title", String.class),
                sectionConfigList);
    }

    public PageConfig(String title, List<SectionConfig> sectionConfigList) {
        this.title = title;
        this.sectionConfigList = sectionConfigList;
    }

    public String getTitle() {
        return title;
    }

    public List<SectionConfig> getSectionConfigList() {
        return sectionConfigList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        if (sectionConfigList == null) {
            dest.writeInt(0);
        } else {
            dest.writeInt(sectionConfigList.size());
            for (SectionConfig sectionConfig : sectionConfigList) {
                dest.writeParcelable(sectionConfig, flags);
            }
        }

        dest.writeTypedList(sectionConfigList);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PageConfig that = (PageConfig) o;

        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        return sectionConfigList.equals(that.sectionConfigList);

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + sectionConfigList.hashCode();
        return result;
    }
}
