package com.mstage.appkit.util;

import android.support.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.Iterator;

/**
 * Created by Khang NT on 5/5/17.
 * Email: khang.neon.1997@gmail.com
 */

public class DataSnapshotWrapper {
    private DataSnapshot dataSnapshot;

    public DataSnapshotWrapper(DataSnapshot dataSnapshot) {
        this.dataSnapshot = Preconditions.checkNotNull(dataSnapshot);
    }

    public DataSnapshotWrapper child(String name) {
        return new DataSnapshotWrapper(dataSnapshot.child(name));
    }

    public Iterable<DataSnapshotWrapper> getChildren() {
        final Iterable<DataSnapshot> children = dataSnapshot.getChildren();
        return () -> new Iterator<DataSnapshotWrapper>() {
            @Override
            public boolean hasNext() {
                return children.iterator().hasNext();
            }

            @Override
            public DataSnapshotWrapper next() {
                return new DataSnapshotWrapper(children.iterator().next());
            }
        };
    }

    public boolean hasChild(String name) {
        return dataSnapshot.hasChild(name);
    }

    public boolean hasChildren() {
        return dataSnapshot.hasChildren();
    }

    public <T> T get(String key, Class<T> tClass) {
        return get(key, tClass, null);
    }

    public <T> T get(String key, Class<T> tClass, @Nullable T defaultValue) {
        T value = dataSnapshot.child(key).getValue(tClass);
        if (value == null) return defaultValue;
        return value;
    }

    public <T> T get(String key, GenericTypeIndicator<T> genericType) {
        return dataSnapshot.child(key).getValue(genericType);
    }
}
