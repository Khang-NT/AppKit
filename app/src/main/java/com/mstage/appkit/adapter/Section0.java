package com.mstage.appkit.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.marverenic.adapter.EnhancedViewHolder;
import com.marverenic.adapter.HeterogeneousAdapter;
import com.mstage.appkit.databinding.Section0Binding;
import com.mstage.appkit.model.MovieData;
import com.mstage.appkit.section.Section0ItemConfig;

import java.util.List;

/**
 * Created by Khang NT on 5/23/17.
 * Email: khang.neon.1997@gmail.com
 */

public class Section0 extends HeterogeneousAdapter.ListSection<MovieData> {

    private Section0ItemConfig mItemConfig;

    /**
     * @param data The data to populate this Section with
     */
    public Section0(@NonNull List<MovieData> data, @NonNull Section0ItemConfig itemConfig) {
        super(data);
        mItemConfig = itemConfig;
    }

    @Override
    public EnhancedViewHolder<MovieData> createViewHolder(HeterogeneousAdapter adapter, ViewGroup parent) {
        return new ViewHolder(Section0Binding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    private class ViewHolder extends EnhancedViewHolder<MovieData> {
        Section0Binding mBinding;

        public ViewHolder(Section0Binding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
            this.mBinding.setItemConfig(mItemConfig);
        }

        @Override
        public void onUpdate(MovieData item, int position) {
            mBinding.setMovieData(item);
        }
    }
}
