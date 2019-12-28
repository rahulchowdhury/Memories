package co.rahulchowdhury.memories.data.source.remote

import co.rahulchowdhury.memories.data.model.remote.User

class RandomUsersRemoteSource(
    private val randomUsersApiService: RandomUsersApiService
) : UsersRemoteSource {

    override suspend fun fetchUsers(
        limit: Int
    ): List<User> = randomUsersApiService.fetchRandomUsers(limit).results

}
