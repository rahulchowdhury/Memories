package co.rahulchowdhury.memories.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import co.rahulchowdhury.memories.data.model.local.Photo
import co.rahulchowdhury.memories.data.model.remote.ResponseState
import co.rahulchowdhury.memories.data.repo.PhotosRepository

class GalleryViewModel(
    photosRepository: PhotosRepository
) : ViewModel() {

    val photos: LiveData<PagedList<Photo>>
    val error: LiveData<ResponseState>

    init {
        photosRepository.coroutineScope = viewModelScope
        val photosResponsePair = photosRepository.loadPhotos()

        photos = photosResponsePair.first
        error = photosResponsePair.second
    }

}
