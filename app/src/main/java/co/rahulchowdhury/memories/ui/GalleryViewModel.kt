package co.rahulchowdhury.memories.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import co.rahulchowdhury.memories.data.model.local.Photo
import co.rahulchowdhury.memories.data.repo.PhotosRepository

class GalleryViewModel(
    private val photosRepository: PhotosRepository
) : ViewModel() {

    lateinit var photos: LiveData<PagedList<Photo>>

    fun loadPhotos() {
        photosRepository.coroutineScope = viewModelScope

        photos = photosRepository.loadPhotos()
    }
}
