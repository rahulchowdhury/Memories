package co.rahulchowdhury.memories.data.source.local

import androidx.paging.DataSource
import co.rahulchowdhury.memories.data.model.local.Photo

class DefaultPhotosLocalSource(
    private val photosDao: PhotosDao
) : PhotosLocalSource {

    override suspend fun save(photos: List<Photo>) {
        photosDao.save(photos)
    }

    override fun loadAll(): DataSource.Factory<Int, Photo> = photosDao.loadAll()

}
