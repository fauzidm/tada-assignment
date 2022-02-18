package dev.illwiz.tada.data.utils

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout

class CustomFrameLayout:FrameLayout {
	var interceptTouch = false

	constructor(context: Context) : super(context)
	constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
	constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

	override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
		if(interceptTouch) {
			return true
		}
		return super.onInterceptTouchEvent(ev)
	}

}