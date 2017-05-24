package com.mstage.appkit.section;

import android.os.Parcel;
import android.util.SparseArray;

import com.mstage.appkit.util.DataSnapshotWrapper;

import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

/**
 * Created by Khang NT on 5/23/17.
 * Email: khang.neon.1997@gmail.com
 */

public class SectionMap {

    private static final SectionMap sInstance = new SectionMap();

    public static SectionMap getInstance() {
        return sInstance;
    }

    private final SparseArray<BiFunction<Integer, Parcel, SectionConfig>> mSectionConfigParcelCreator;
    private final SparseArray<Function<DataSnapshotWrapper, SectionConfig>> mSectionConfigDataSnapshotCreator;

    public SectionMap() {
        mSectionConfigParcelCreator = new SparseArray<>();
        mSectionConfigDataSnapshotCreator = new SparseArray<>();

        mSectionConfigParcelCreator.put(0, Section0Config::new);
        mSectionConfigDataSnapshotCreator.put(0, Section0Config::from);
    }

    public SectionConfig getSectionConfigFromParcel(int type, Parcel parcel) {
        BiFunction<Integer, Parcel, SectionConfig> creator = mSectionConfigParcelCreator.get(type);
        if (creator == null)
            throw new IllegalArgumentException("Unknown section type: " + type);
        try {
            return creator.apply(type, parcel);
        } catch (Exception e) {
            throw new RuntimeException("Can't create section config from parcel with type: " + type);
        }
    }

    public SectionConfig getSectionConfigFromDataSnapshot(DataSnapshotWrapper dataSnapshot) {
        int type = dataSnapshot.get("type", Integer.class, Integer.MAX_VALUE);
        Function<DataSnapshotWrapper, SectionConfig> creator = mSectionConfigDataSnapshotCreator.get(type);
        if (creator == null) {
            throw new IllegalArgumentException("Unknown section type from: " + dataSnapshot.toString());
        }
        try {
            return creator.apply(dataSnapshot);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create section config from: " + dataSnapshot, e);
        }
    }
}
