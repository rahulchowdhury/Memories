package co.rahulchowdhury.memories.data.source.remote

import co.rahulchowdhury.memories.data.model.remote.RemotePhoto

class RandomPhotosRemoteSource(
    private val randomUsersApiService: RandomUsersApiService
) : PhotosRemoteSource {

    override suspend fun fetchPhotos(
        limit: Int,
        page: Int
    ): List<RemotePhoto> = randomUsersApiService.fetchRandomUsers(limit, page).results

}
