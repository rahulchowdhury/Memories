package co.rahulchowdhury.monet

import android.widget.ImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun ImageView.load(url: String, block: MonetContext.() -> Unit) {
    val monetContext = MonetContext().apply(block)
    val coroutineScope = CoroutineScope(Dispatchers.Main + monetContext.job)

    coroutineScope.launch {
        val bitmap = loadIntoBitmap(resources, url)
        setImageBitmap(bitmap)
    }
}
