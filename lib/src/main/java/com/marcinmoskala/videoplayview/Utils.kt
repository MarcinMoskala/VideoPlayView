package com.marcinmoskala.videoplayview

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View

internal fun <T : View> View.bindView(viewId: Int) = lazy { findViewById<T>(viewId) }

internal fun View.touchBasedOnClick(onClick: () -> Unit) {
    var actionDownTouch: MotionEvent? = null
    setOnTouchListener { v, e ->
        when (e.action) {
            KeyEvent.ACTION_DOWN -> {
                actionDownTouch = e
            }
            KeyEvent.ACTION_UP -> {
                actionDownTouch?.let { e2 ->
                    if (Math.abs(e.x - e2.x) + Math.abs(e.y - e2.y) < 30) {
                        onClick()
                    }
                }
            }
        }
        true
    }
}

internal fun View.getBackgroundColor(): Int {
    var color = Color.BLACK
    val bg = background
    if (bg is ColorDrawable) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            val bitmap  = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
            val bounds = Rect()
            bounds.set(bg.bounds) // Save the original bounds.
            bg.setBounds(0, 0, 1, 1) // Change the bounds.
            bg.draw(Canvas(bitmap))
            color = bitmap!!.getPixel(0, 0)
            bg.bounds = bounds // Restore the original bounds.
        } else {
            color = bg.color
        }
    }
    return color
}

internal val View.visibleOnScreen: Boolean
    get() {
        val rect = Rect()
        return getGlobalVisibleRect(rect) && rect.height() != 0 && rect.width() != 0
    }