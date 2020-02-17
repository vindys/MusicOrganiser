package com.vindys.musicorganiser.main.adapter;


import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vindys.musicorganiser.R;
import com.vindys.musicorganiser.data.local.entity.Song;
import com.vindys.musicorganiser.databinding.ListItemSongBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by Jeffrey Liu on 3/21/16.
 */
public class HorizontalRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Song> mList;
    Context mContext;
    private OnItemClickListener mItemClickListener;

    HorizontalRecyclerAdapter() {
    }

    void updateList(List<Song> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    private class CellViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        final ListItemSongBinding binding;

        CellViewHolder(ListItemSongBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            // This code is used to get the screen dimensions of the user's device
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity)mContext).getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);

            int width = displayMetrics.widthPixels;

            // Set the ViewHolder width to be a third of the screen size, and height to wrap content
            itemView.setLayoutParams(new RecyclerView.LayoutParams((int)Math.round(width*0.60), RecyclerView.LayoutParams.WRAP_CONTENT));
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(view, getLayoutPosition());
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemLongClick(view, getLayoutPosition());
                return true;
            }
            return false;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int viewType) {
        mContext = viewGroup.getContext();
        ListItemSongBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.list_item_song,
                        viewGroup, false);
        return new CellViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        CellViewHolder cellViewHolder = (CellViewHolder) viewHolder;
        cellViewHolder.binding.executePendingBindings();
        cellViewHolder.binding.setSong( mList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mList == null)
            return 0;
        return mList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    // for both short and long click
    void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}