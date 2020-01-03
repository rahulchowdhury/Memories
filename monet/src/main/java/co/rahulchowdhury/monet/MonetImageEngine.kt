package co.rahulchowdhury.monet

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.collection.LruCache
import androidx.core.graphics.drawable.toDrawable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.ref.SoftReference
import java.net.URL
import java.util.*
import kotlin.collections.HashSet

val reusableBitmaps: MutableSet<SoftReference<Bitmap>> = Collections.synchronizedSet(
    HashSet<SoftReference<Bitmap>>()
)

val memoryCache: LruCache<String, BitmapDrawable> by lazy {
    val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
    val cacheSize = maxMemory / 8

    object : LruCache<String, BitmapDrawable>(cacheSize) {

        override fun sizeOf(key: String, bitmapDrawable: BitmapDrawable): Int {
            return bitmapDrawable.bitmap.byteCount / 1024
        }

        override fun entryRemoved(
            evicted: Boolean,
            key: String,
            oldValue: BitmapDrawable,
            newValue: BitmapDrawable?
        ) {
            if (oldValue is RecyclingBitmapDrawable) {
                oldValue.setIsCached(false)
            } else {
                reusableBitmaps.add(SoftReference(oldValue.bitmap))
            }
        }

    }
}

internal suspend fun loadIntoBitmap(resources: Resources, url: String): Bitmap? =
    withContext(Dispatchers.IO) {
        val cachedBitmap = memoryCache[url]
        cachedBitmap?.let { return@withContext it.bitmap }

        URL(url).openStream().use {
            // We are not using decoding options to sample
            // the sample the image here because the image
            // coming from the server is already a low-res
            // image. Therefore, we are intentionally skipping
            // the extra processing (calculating bitmap dimens, etc)
            val bitmapConfig = BitmapFactory.Options().apply {
                inPreferredConfig = Bitmap.Config.RGB_565
            }

            markInBitmapOptions(bitmapConfig)

            val decodedBitmap = BitmapFactory.decodeStream(it, null, bitmapConfig)

            decodedBitmap?.let { memoryCache.put(url, decodedBitmap.toDrawable(resources)) }

            return@withContext decodedBitmap
        }
    }

internal fun markInBitmapOptions(options: BitmapFactory.Options) {
    options.inMutable = true

    getBitmapFromReusableSet()?.also { bitmap ->
        options.inBitmap = bitmap
    }
}

internal fun getBitmapFromReusableSet(): Bitmap? {
    reusableBitmaps.takeIf { it.isNotEmpty() }?.let { reusableBitmaps ->
        synchronized(reusableBitmaps) {
            val iterator: MutableIterator<SoftReference<Bitmap>> = reusableBitmaps.iterator()
            while (iterator.hasNext()) {
                iterator.next().get()?.let { item ->
                    if (item.isMutable) {
                        iterator.remove()
                        return item
                    } else {
                        iterator.remove()
                    }
                }
            }
        }
    }

    return null
}
