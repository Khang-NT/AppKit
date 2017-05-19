package com.mstage.appkit.data.store;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mstage.appkit.model.FlashScreenConfig;
import com.mstage.appkit.model.MainScreenConfig;
import com.mstage.appkit.util.DataSnapshotWrapper;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import timber.log.Timber;

/**
 * Created by Khang NT on 5/5/17.
 * Email: khang.neon.1997@gmail.com
 */

public class FirebaseConfigStore implements ConfigurationStore, ValueEventListener {
    private final Object lock = new Object();
    private DatabaseReference rootReference;
    private boolean isListening;

    private BehaviorSubject<FlashScreenConfig> flashScreenConfigSubject;
    private BehaviorSubject<MainScreenConfig> mainScreenConfigSubject;

    public FirebaseConfigStore(DatabaseReference rootReference) {
        this.rootReference = rootReference;
        this.isListening = false;

        this.flashScreenConfigSubject = BehaviorSubject.create();
        this.mainScreenConfigSubject = BehaviorSubject.create();
        ensureListenerWorking();
    }

    void ensureListenerWorking() {
        synchronized (lock) {
            if (!isListening) {
                rootReference.addValueEventListener(this);
                isListening = true;
            }
        }
    }

    @Override
    public Observable<FlashScreenConfig> getFlashScreenConfig() {
        ensureListenerWorking();
        return flashScreenConfigSubject;
    }

    @Override
    public Observable<MainScreenConfig> getMainScreenConfig() {
        ensureListenerWorking();
        return mainScreenConfigSubject;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        DataSnapshotWrapper dataSnapshotWrapper = new DataSnapshotWrapper(dataSnapshot);

        FlashScreenConfig flashScreenConfig = FlashScreenConfig.from(dataSnapshotWrapper.child("FlashScreen"));
        if (!flashScreenConfig.equals(flashScreenConfigSubject.getValue()))
            flashScreenConfigSubject.onNext(flashScreenConfig);

        MainScreenConfig mainScreenConfig = MainScreenConfig.from(dataSnapshotWrapper.child("MainScreen"));
        if (!mainScreenConfig.equals(mainScreenConfigSubject.getValue()))
            mainScreenConfigSubject.onNext(mainScreenConfig);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        synchronized (lock) {
            isListening = false;
            Timber.e(databaseError.toException(), "Firebase listener connection is broken.");
        }
    }
}
