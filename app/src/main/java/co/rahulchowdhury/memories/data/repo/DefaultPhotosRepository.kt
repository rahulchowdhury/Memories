package co.rahulchowdhury.memories.data.repo

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import co.rahulchowdhury.memories.data.Constants
import co.rahulchowdhury.memories.data.model.local.Photo
import co.rahulchowdhury.memories.data.model.remote.ResponseState
import co.rahulchowdhury.memories.data.paging.PhotosBoundaryCallback
import co.rahulchowdhury.memories.data.source.local.PhotosLocalSource
import co.rahulchowdhury.memories.data.source.remote.PhotosRemoteSource
import kotlinx.coroutines.CoroutineScope

class DefaultPhotosRepository(
    private val photosLocalSource: PhotosLocalSource,
    private val photosRemoteSource: PhotosRemoteSource
) : PhotosRepository {

    override lateinit var coroutineScope: CoroutineScope

    override fun loadPhotos(): Pair<LiveData<PagedList<Photo>>, LiveData<ResponseState>> {
        val photosDataSourceFactory = photosLocalSource.loadAll()

        val photosBoundaryCallback = PhotosBoundaryCallback(
            coroutineScope = coroutineScope,
            photosLocalSource = photosLocalSource,
            photosRemoteSource = photosRemoteSource
        )

        val config = PagedList.Config.Builder()
            .setInitialLoadSizeHint(Constants.Paging.FIRST_LOAD_SIZE)
            .setPageSize(Constants.Paging.PAGE_SIZE)
            .build()

        val pagedList =  LivePagedListBuilder(photosDataSourceFactory, config)
            .setBoundaryCallback(photosBoundaryCallback)
            .build()

        return Pair(pagedList, photosBoundaryCallback.networkResponseState)
    }

}
