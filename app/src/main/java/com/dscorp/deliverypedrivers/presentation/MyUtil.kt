package com.dscorp.deliverypedrivers.presentation

import android.content.Context

object MyUtil {
    fun dp2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}