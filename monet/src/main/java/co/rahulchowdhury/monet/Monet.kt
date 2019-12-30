package co.rahulchowdhury.monet

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun loadUsingMonet(coroutineScope: CoroutineScope, block: MonetContext.() -> Unit) {
    val monetContext = MonetContext().apply(block)

    coroutineScope.launch {
        val bitmap = loadIntoBitmap(monetContext.url)
        monetContext.targetImageView?.setImageBitmap(bitmap)
    }
}
