package com.example.andersentask4contacts

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
//creating divider line(gray) between contacts in recyclerView
class DividerItemDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private val paint = Paint().apply {
        color = ContextCompat.getColor(context, android.R.color.darker_gray)
        strokeWidth = context.resources.displayMetrics.density * 1
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0 until childCount - 1){
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + paint.strokeWidth
            c.drawLine(left.toFloat(), top.toFloat(), right.toFloat(), bottom, paint)
        }
    }
}