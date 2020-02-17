package com.vindys.musicorganiser.main.adapter;


import android.content.Context;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.vindys.musicorganiser.R;
import com.vindys.musicorganiser.data.local.entity.Song;
import com.vindys.musicorganiser.data.model.SortedSongs;
import com.vindys.musicorganiser.databinding.ListItemCriteriaBinding;
import com.vindys.musicorganiser.main.itemdecorator.SpacesItemDecoration;
import com.vindys.musicorganiser.utils.StartSnapHelper;

import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by Jeffrey Liu on 3/21/16.
 */
public class VerticalRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SortedSongs> songs;
    private Integer spanCount = 3;
    private SparseIntArray listPosition = new SparseIntArray();
    private HorizontalRecyclerAdapter.OnItemClickListener mItemClickListener;
    private Context mContext;
    private RecyclerView.RecycledViewPool viewPool;

    public VerticalRecyclerAdapter() {

        viewPool = new RecyclerView.RecycledViewPool();
    }

    public void setSongs(List<SortedSongs> songs) {
        this.songs = songs;
        notifyDataSetChanged();
    }

    public void setSpanCount(Integer spanCount){
        this.spanCount = spanCount;
        notifyDataSetChanged();
    }

    private class CellViewHolder extends RecyclerView.ViewHolder {
        final ListItemCriteriaBinding binding;
        private RecyclerView mRecyclerView;
        private HorizontalRecyclerAdapter adapter;
        private GridLayoutManager layoutManager;
        private int spanCount=2;

        public CellViewHolder(ListItemCriteriaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            mRecyclerView = itemView.findViewById(R.id.recyclerView);
            mRecyclerView.setRecycledViewPool(viewPool);

            mRecyclerView.setHasFixedSize(true);
            layoutManager = new GridLayoutManager(mContext,spanCount);
            layoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
            mRecyclerView.setLayoutManager(layoutManager);
            //setItemDecoration();

            adapter = new HorizontalRecyclerAdapter();
            adapter.SetOnItemClickListener(mItemClickListener);
            mRecyclerView.setAdapter(adapter);


            // this is needed if you are working with CollapsingToolbarLayout,
            // I am adding this here just in case I forget.
            mRecyclerView.setNestedScrollingEnabled(false);

            //optional
            StartSnapHelper snapHelper = new StartSnapHelper();
            snapHelper.attachToRecyclerView(mRecyclerView);

        }

        public void setData(List<Song> list) {
            adapter.updateList(list);
        }

        void setSpanCount(int spanCount) {
            this.spanCount = spanCount;
            ((GridLayoutManager) mRecyclerView.getLayoutManager()).setSpanCount(spanCount);
            setItemDecoration();
        }

        private void setItemDecoration(){
            int spacingXInPixels = mContext.getResources().getDimensionPixelSize(R.dimen.spacingX);
            int spacingYInPixels = mContext.getResources().getDimensionPixelSize(R.dimen.spacingY);
            while (mRecyclerView.getItemDecorationCount() > 0) {
                mRecyclerView.removeItemDecorationAt(0);
            }
            mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingXInPixels,spacingYInPixels,spanCount));
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int viewType) {
        mContext = viewGroup.getContext();

        ListItemCriteriaBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.list_item_criteria,
                        viewGroup, false);
        return new CellViewHolder(binding);


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        switch (viewHolder.getItemViewType()) {
            default: {
                CellViewHolder cellViewHolder = (CellViewHolder) viewHolder;

                List<Song> songList = songs.get(position).getSongName();
                cellViewHolder.setData(songList);

                cellViewHolder.setSpanCount((songList.size()>spanCount)?spanCount:songList.size());
                cellViewHolder.binding.executePendingBindings();
                cellViewHolder.binding.setTitle(songs.get(position).getName());

                int lastSeenFirstPosition = listPosition.get(position, 0);
                if (lastSeenFirstPosition >= 0) {
                    cellViewHolder.layoutManager.scrollToPositionWithOffset(lastSeenFirstPosition, 0);
                }
                break;
            }
        }
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder viewHolder) {
        final int position = viewHolder.getAdapterPosition();
        CellViewHolder cellViewHolder = (CellViewHolder) viewHolder;
        int firstVisiblePosition = cellViewHolder.layoutManager.findFirstVisibleItemPosition();
        listPosition.put(position, firstVisiblePosition);

        super.onViewRecycled(viewHolder);
    }


    @Override
    public int getItemCount() {
        if (songs == null)
            return 0;
        return songs.size();
    }


    // for both short and long click
    public void SetOnItemClickListener(final HorizontalRecyclerAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}