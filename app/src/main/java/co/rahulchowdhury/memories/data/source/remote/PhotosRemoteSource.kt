package co.rahulchowdhury.memories.data.source.remote

import co.rahulchowdhury.memories.data.model.remote.RemotePhoto

interface PhotosRemoteSource {

    suspend fun fetchPhotos(limit: Int, page: Int): List<RemotePhoto>

}
