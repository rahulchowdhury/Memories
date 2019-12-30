package co.rahulchowdhury.monet

import android.widget.ImageView

data class MonetContext(
    var url: String = "",
    var placeholder: String = "",
    var targetImageView: ImageView? = null
)
