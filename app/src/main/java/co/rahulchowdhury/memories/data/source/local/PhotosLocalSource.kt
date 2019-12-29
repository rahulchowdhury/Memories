package co.rahulchowdhury.memories.data.source.local

import androidx.paging.DataSource
import co.rahulchowdhury.memories.data.model.local.Photo

interface PhotosLocalSource {

    suspend fun save(photos: List<Photo>)

    fun loadAll(): DataSource.Factory<Int, Photo>

}
