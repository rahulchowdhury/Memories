package co.rahulchowdhury.memories.data.source.remote

import co.rahulchowdhury.memories.data.model.remote.User

interface UsersRemoteSource {

    suspend fun fetchUsers(limit: Int, page: Int): List<User>

}
