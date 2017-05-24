package com.mstage.appkit.section;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Parcel;
import android.text.TextUtils;

import com.marverenic.adapter.HeterogeneousAdapter;
import com.mstage.appkit.adapter.HeaderSection;
import com.mstage.appkit.adapter.Section0;
import com.mstage.appkit.model.HttpDataSourceConfig;
import com.mstage.appkit.model.MovieData;
import com.mstage.appkit.util.DataSnapshotWrapper;

import java.util.Collections;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Khang NT on 5/22/17.
 * Email: khang.neon.1997@gmail.com
 */

@SuppressLint("ParcelCreator")
public class Section0Config extends SectionConfig {
    private String title;
    private HttpDataSourceConfig data;
    private Section0ItemConfig itemConfig;

    public static Section0Config from(DataSnapshotWrapper dataSnapshot) {
        return new Section0Config(dataSnapshot.get("type", Integer.class, Integer.MAX_VALUE),
                dataSnapshot.get("title", String.class),
                HttpDataSourceConfig.from(dataSnapshot.child("data")),
                Section0ItemConfig.from(dataSnapshot.child("item_config")));
    }

    public Section0Config(int type, String title, HttpDataSourceConfig data, Section0ItemConfig itemConfig) {
        super(type);
        this.title = title;
        this.data = data;
        this.itemConfig = itemConfig;
    }

    public String getTitle() {
        return title;
    }

    public HttpDataSourceConfig getData() {
        return data;
    }

    public Section0ItemConfig getItemConfig() {
        return itemConfig;
    }

    @Override
    public Completable addToAdapter(Context context, HeterogeneousAdapter adapter) {
        if (!TextUtils.isEmpty(getTitle()))
            adapter.addSection(new HeaderSection(getTitle()));

        final Section0 section = new Section0(Collections.emptyList(), getItemConfig());
        adapter.addSection(section);
        return getData().executeRequest(context, MovieData.movieDataListTransformer())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(section::setData)
                .toCompletable();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.title);
        dest.writeParcelable(this.data, flags);
        dest.writeParcelable(this.itemConfig, flags);
    }

    protected Section0Config(int type, Parcel in) {
        super(type);
        this.title = in.readString();
        this.data = in.readParcelable(HttpDataSourceConfig.class.getClassLoader());
        this.itemConfig = in.readParcelable(Section0ItemConfig.class.getClassLoader());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Section0Config that = (Section0Config) o;

        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (data != null ? !data.equals(that.data) : that.data != null) return false;
        return itemConfig != null ? itemConfig.equals(that.itemConfig) : that.itemConfig == null;

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (itemConfig != null ? itemConfig.hashCode() : 0);
        return result;
    }
}
