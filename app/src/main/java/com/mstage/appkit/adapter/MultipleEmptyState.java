package com.mstage.appkit.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marverenic.adapter.EmptyState;
import com.marverenic.adapter.EnhancedViewHolder;
import com.mstage.appkit.databinding.MultipleEmptyStateBinding;

/**
 * Created by Khang NT on 3/28/17.
 * Email: khang.neon.1997@gmail.com
 */

public class MultipleEmptyState extends EmptyState {

    public static final int STATE_LOADING = 1, STATE_SUCCESS = 2, STATE_FAILED = 3;

    private MultipleEmptyStateBinding mBinding;
    private int state;
    private String loadingMessage, errorMessage, emptyMessage;
    private String errorReason;
    private View.OnClickListener onRetryClick;


    public MultipleEmptyState(String loadingMessage, String errorMessage, String emptyMessage, View.OnClickListener onRetryClick) {
        this.loadingMessage = loadingMessage;
        this.errorMessage = errorMessage;
        this.emptyMessage = emptyMessage;
        this.onRetryClick = onRetryClick;
        this.state = STATE_SUCCESS;
    }

    public MultipleEmptyState setState(int state) {
        this.state = state;
        if (mBinding != null) mBinding.setState(state);
        return this;
    }

    public MultipleEmptyState setErrorReason(String errorReason) {
        this.errorReason = errorReason;
        if (mBinding != null) mBinding.setErrorReason(errorReason);
        return this;
    }

    @Override
    public View onCreateView(RecyclerView.Adapter<EnhancedViewHolder> adapter, ViewGroup parent) {
        mBinding = MultipleEmptyStateBinding.inflate(LayoutInflater.from(parent.getContext()),
            parent, false);
        mBinding.setEmptyMessage(emptyMessage);
        mBinding.setErrorMessage(errorMessage);
        mBinding.setLoadingMessage(loadingMessage);
        mBinding.setErrorReason(errorReason);
        mBinding.setOnRetryClick(onRetryClick);
        mBinding.setState(state);
        return mBinding.getRoot();
    }

    @Override
    public void onUpdate(View emptyStateView) {
        mBinding.executePendingBindings();
    }
}
