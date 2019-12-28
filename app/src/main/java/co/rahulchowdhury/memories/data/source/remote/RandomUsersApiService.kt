package co.rahulchowdhury.memories.data.source.remote

import co.rahulchowdhury.memories.data.model.remote.RandomUsers
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUsersApiService {

    @GET("/api")
    suspend fun fetchRandomUsers(
        @Query("results") limit: Int
    ): RandomUsers

}
