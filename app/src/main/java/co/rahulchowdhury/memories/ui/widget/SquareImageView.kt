package co.rahulchowdhury.memories.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

class SquareImageView(
    context: Context,
    attributeSet: AttributeSet
) : ImageView(context, attributeSet) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

}
