package co.rahulchowdhury.memories.data.paging

import androidx.paging.PagedList
import co.rahulchowdhury.memories.data.Constants
import co.rahulchowdhury.memories.data.model.local.Photo
import co.rahulchowdhury.memories.data.model.remote.toPhoto
import co.rahulchowdhury.memories.data.source.local.PhotosLocalSource
import co.rahulchowdhury.memories.data.source.remote.PhotosRemoteSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PhotosBoundaryCallback(
    private val coroutineScope: CoroutineScope,
    private val photosLocalSource: PhotosLocalSource,
    private val photosRemoteSource: PhotosRemoteSource
) : PagedList.BoundaryCallback<Photo>() {

    private var currentPage = 1
    private var isLoadingData = false

    override fun onZeroItemsLoaded() {
        fetchAndSavePhotos()
    }

    override fun onItemAtEndLoaded(itemAtEnd: Photo) {
        fetchAndSavePhotos()
    }

    private fun fetchAndSavePhotos() {
        if (isLoadingData) {
            return
        }

        isLoadingData = true

        coroutineScope.launch {
            val remotePhotos = photosRemoteSource.fetchPhotos(
                limit = Constants.Remote.PAGE_SIZE,
                page = currentPage
            )

            val photos = remotePhotos.map { it.toPhoto() }
            photosLocalSource.save(photos)

            isLoadingData = false
        }
    }

}
