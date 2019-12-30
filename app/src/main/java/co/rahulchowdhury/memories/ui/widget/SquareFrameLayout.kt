package co.rahulchowdhury.memories.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

class SquareFrameLayout(
    context: Context,
    attributeSet: AttributeSet
) : FrameLayout(context, attributeSet) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

}
