package co.rahulchowdhury.monet

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.collection.LruCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

val memoryCache: LruCache<String, Bitmap> by lazy {
    val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
    val cacheSize = maxMemory / 8

    object : LruCache<String, Bitmap>(cacheSize) {

        override fun sizeOf(key: String, bitmap: Bitmap): Int {
            return bitmap.byteCount / 1024
        }

    }
}

internal suspend fun loadIntoBitmap(url: String): Bitmap =
    withContext(Dispatchers.IO) {
        val cachedBitmap = memoryCache[url]
        cachedBitmap?.let { return@withContext it }

        URL(url).openStream().use {
            // We are not using decoding options to sample
            // the sample the image here because the image
            // coming from the server is already a low-res
            // image. Therefore, we are intentionally skipping
            // the extra processing (calculating bitmap dimens, etc)
            val decodedBitmap = BitmapFactory.decodeStream(it)
            memoryCache.put(url, decodedBitmap)

            return@withContext decodedBitmap
        }
    }
