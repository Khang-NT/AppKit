package com.mstage.appkit.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marverenic.adapter.HeterogeneousAdapter;
import com.mstage.appkit.R;
import com.mstage.appkit.adapter.MultipleEmptyState;
import com.mstage.appkit.model.PageConfig;
import com.mstage.appkit.util.Preconditions;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Khang NT on 5/22/17.
 * Email: khang.neon.1997@gmail.com
 */

public class PageFragment extends RxFragment {
    public static final String KEY_PAGE_CONFIG = "key:page_config";

    private HeterogeneousAdapter mAdapter;
    private MultipleEmptyState mMultipleEmptyState;
    private PageConfig mPageConfig;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mPageConfig = Preconditions.checkNotNull(getArguments().getParcelable(KEY_PAGE_CONFIG));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);

        if (mAdapter == null) {
            mAdapter = new HeterogeneousAdapter();
            mMultipleEmptyState = new MultipleEmptyState("Loading content...",
                    "Error occurs while loading content.",
                    "Content empty", v -> loadData());
            mAdapter.setEmptyState(mMultipleEmptyState);
            loadData();
        }

        recyclerView.setAdapter(mAdapter);
    }

    void loadData() {
        mMultipleEmptyState.setState(MultipleEmptyState.STATE_LOADING);
        while (mAdapter.getSectionCount() > 0) mAdapter.removeSection(0);
        Completable.concat(Flowable.fromIterable(mPageConfig.getSectionConfigList())
                .map(sectionConfig -> sectionConfig.addToAdapter(getActivity().getApplicationContext(), mAdapter)))
                .compose(bindUntilEvent(FragmentEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    mMultipleEmptyState.setState(MultipleEmptyState.STATE_SUCCESS);
                    mAdapter.notifyDataSetChanged();
                }, error -> {
                    mMultipleEmptyState.setState(MultipleEmptyState.STATE_FAILED);
                    mMultipleEmptyState.setErrorReason(error.getMessage());
                    mAdapter.notifyDataSetChanged();
                    error.printStackTrace();
                });
    }
}
