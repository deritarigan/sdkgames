package com.akggames.akg_sdk.ui.component

import android.content.Context
import android.view.MotionEvent
import android.view.View
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.core.view.ViewCompat.animate
import android.R.attr.x
import android.R.attr.y
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.util.AttributeSet
import android.view.ViewGroup


class mFloatingActionButton(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : FloatingActionButton(
    context,
    attrs,
    defStyleAttr
), View.OnTouchListener {
    var downRawX: Float = 0f
    var downRawY: Float = 0f
    var dX: Float = 0f
    var dY: Float = 0f

    init {
        setOnTouchListener(this)
    }

    override fun onTouch(view: View?, motionEvent: MotionEvent?): Boolean {
        val layoutParams = view?.getLayoutParams() as ViewGroup.MarginLayoutParams

        val action = motionEvent?.getAction()
        if (action == MotionEvent.ACTION_DOWN) {

            downRawX = motionEvent.getRawX()
            downRawY = motionEvent.getRawY()
            dX = view.getX() - downRawX
            dY = view.getY() - downRawY

            return true // Consumed

        } else if (action == MotionEvent.ACTION_MOVE) {

            val viewWidth = view.getWidth()
            val viewHeight = view.getHeight()

            val viewParent = view.getParent() as View
            val parentWidth = viewParent.width
            val parentHeight = viewParent.height

            var newX = motionEvent.getRawX() + dX
            newX = Math.max(
                layoutParams.leftMargin.toFloat(),
                newX
            ) // Don't allow the FAB past the left hand side of the parent
            newX = Math.min(
                (parentWidth - viewWidth - layoutParams.rightMargin).toFloat(),
                newX
            ) // Don't allow the FAB past the right hand side of the parent

            var newY = motionEvent.getRawY() + dY
            newY = Math.max(layoutParams.topMargin.toFloat(), newY) // Don't allow the FAB past the top of the parent
            newY = Math.min(
                (parentHeight - viewHeight - layoutParams.bottomMargin).toFloat(),
                newY
            ) // Don't allow the FAB past the bottom of the parent

            view.animate()
                .x(newX)
                .y(newY)
                .setDuration(0)
                .start()

            return true // Consumed

        } else if (action == MotionEvent.ACTION_UP) {

            val upRawX = motionEvent.getRawX()
            val upRawY = motionEvent.getRawY()

            val upDX = upRawX - downRawX
            val upDY = upRawY - downRawY

            return if (Math.abs(upDX) < 10 && Math.abs(upDY) < 10) { // A click
                performClick()
            } else { // A drag
                true // Consumed
            }

        } else {
            return super.onTouchEvent(motionEvent)
        }
    }
}