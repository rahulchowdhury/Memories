package co.rahulchowdhury.memories.data.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import co.rahulchowdhury.memories.data.Constants
import co.rahulchowdhury.memories.data.model.local.Photo
import co.rahulchowdhury.memories.data.model.remote.Error
import co.rahulchowdhury.memories.data.model.remote.ResponseState
import co.rahulchowdhury.memories.data.model.remote.Success
import co.rahulchowdhury.memories.data.model.remote.toPhoto
import co.rahulchowdhury.memories.data.source.local.PhotosLocalSource
import co.rahulchowdhury.memories.data.source.remote.PhotosRemoteSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.UnknownHostException

class PhotosBoundaryCallback(
    private val coroutineScope: CoroutineScope,
    private val photosLocalSource: PhotosLocalSource,
    private val photosRemoteSource: PhotosRemoteSource
) : PagedList.BoundaryCallback<Photo>() {

    private var currentPage = 1
    private var isLoadingData = false

    private val _networkResponseState = MutableLiveData<ResponseState>()
    val networkResponseState: LiveData<ResponseState> = _networkResponseState

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
            withContext(Dispatchers.Default) {
                try {
                    val remotePhotos = photosRemoteSource.fetchPhotos(
                        limit = Constants.Remote.PAGE_SIZE,
                        page = currentPage
                    )

                    val photos = remotePhotos.map { it.toPhoto() }
                    photosLocalSource.save(photos)

                    currentPage++
                    isLoadingData = false

                    _networkResponseState.postValue(Success)
                } catch (unknownHostException: UnknownHostException) {
                    _networkResponseState.postValue(Error("Not connected."))
                } catch (exception: Exception) {
                    _networkResponseState.postValue(Error("Server error."))
                }
            }
        }
    }

}
