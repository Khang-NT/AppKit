package com.mstage.appkit.section;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.MainThread;

import com.marverenic.adapter.HeterogeneousAdapter;

import io.reactivex.Completable;

/**
 * Created by Khang NT on 5/23/17.
 * Email: khang.neon.1997@gmail.com
 */

public abstract class SectionConfig implements Parcelable {
    private int type;

    public SectionConfig(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    @MainThread
    public abstract Completable addToAdapter(Context context, HeterogeneousAdapter adapter);

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getType());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static SectionConfig createFromParcel(Parcel in, SectionMap sectionMap) {
        int type = in.readInt();
        return sectionMap.getSectionConfigFromParcel(type, in);
    }
}
