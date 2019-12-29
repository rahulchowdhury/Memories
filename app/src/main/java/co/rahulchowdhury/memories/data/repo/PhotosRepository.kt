package co.rahulchowdhury.memories.data.repo

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import co.rahulchowdhury.memories.data.model.local.Photo
import kotlinx.coroutines.CoroutineScope

interface PhotosRepository {

    var coroutineScope: CoroutineScope

    fun loadPhotos(): LiveData<PagedList<Photo>>

}
