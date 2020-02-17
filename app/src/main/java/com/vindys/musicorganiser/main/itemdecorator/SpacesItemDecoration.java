package com.vindys.musicorganiser.main.itemdecorator;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int spaceX;
    private int spaceY;
    private int span;

    public SpacesItemDecoration(int spaceX, int spaceY, int span) {
        this.spaceX = spaceX;
        this.spaceY = spaceY;
        this.span = span;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = spaceX;
        outRect.right = spaceX;
        outRect.bottom = spaceY;

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildLayoutPosition(view)%span == 0) {
            outRect.top = spaceY;
        } else {
            outRect.top = 0;
        }
    }
}