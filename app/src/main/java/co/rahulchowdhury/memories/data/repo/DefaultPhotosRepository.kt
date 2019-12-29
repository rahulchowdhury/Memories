package co.rahulchowdhury.memories.data.repo

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import co.rahulchowdhury.memories.data.model.local.Photo
import co.rahulchowdhury.memories.data.paging.PhotosBoundaryCallback
import co.rahulchowdhury.memories.data.source.local.PhotosLocalSource
import co.rahulchowdhury.memories.data.source.remote.PhotosRemoteSource
import kotlinx.coroutines.CoroutineScope

class DefaultPhotosRepository(
    private val coroutineScope: CoroutineScope,
    private val photosLocalSource: PhotosLocalSource,
    private val photosRemoteSource: PhotosRemoteSource
) : PhotosRepository {

    override fun loadPhotos(): LiveData<PagedList<Photo>> {
        val photosDataSourceFactory = photosLocalSource.loadAll()

        val photosBoundaryCallback = PhotosBoundaryCallback(
            coroutineScope = coroutineScope,
            photosLocalSource = photosLocalSource,
            photosRemoteSource = photosRemoteSource
        )

        val config = PagedList.Config.Builder()
            .setInitialLoadSizeHint(60)
            .setPageSize(60)
            .build()

        return LivePagedListBuilder(photosDataSourceFactory, config)
            .setBoundaryCallback(photosBoundaryCallback)
            .build()
    }

}
