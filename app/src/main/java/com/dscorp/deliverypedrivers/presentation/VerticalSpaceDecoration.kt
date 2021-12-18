package com.dscorp.deliverypedrivers.presentation

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class SpacesItemDecoration(private val space: Int) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.left = space / 2
        outRect.right = space / 2
        outRect.bottom = space / 2
        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildPosition(view) == 0) outRect.top = space / 2
    }
}