package com.dranoer.giphyapp;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EndlessRecyclerview extends RecyclerView {
    public EndlessRecyclerview(Context context) {
        super(context);
    }

    public EndlessRecyclerview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EndlessRecyclerview(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    AdvancedEndlessRecyclerOnScrollListener aeros;

    @Override
    public void setLayoutManager(LayoutManager layout) {

        if (!(layout instanceof LinearLayoutManager)) {
            throw new RuntimeException();
        }

        aeros = new AdvancedEndlessRecyclerOnScrollListener(layout) {
            @Override
            public void onLoadMore() {
                if (onLoadMoreListener != null)
                    onLoadMoreListener.onLoadMore();
            }
        };

        addOnScrollListener(aeros);
        super.setLayoutManager(layout);
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    private OnLoadMoreListener onLoadMoreListener;

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setLoading(boolean enable) {
        this.aeros.setLoading(enable);
    }


    public abstract class AdvancedEndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
        private int visibleThreshold = 3;
        private int lastVisibleItem, totalItemCount;
        private boolean loading = true;
        private RecyclerView.LayoutManager linearLayoutManager;

        public AdvancedEndlessRecyclerOnScrollListener(RecyclerView.LayoutManager linearLayoutManager) {
            this.linearLayoutManager = linearLayoutManager;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            totalItemCount = linearLayoutManager.getItemCount();
            lastVisibleItem = ((LinearLayoutManager) linearLayoutManager).findLastVisibleItemPosition();
            if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                onLoadMore();
                loading = true;
            }
        }

        public void setLoading(boolean enable) {
            this.loading = enable;
        }

        public abstract void onLoadMore();

    }
}