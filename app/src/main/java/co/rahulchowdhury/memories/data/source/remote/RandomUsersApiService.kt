package co.rahulchowdhury.memories.data.source.remote

import co.rahulchowdhury.memories.data.Constants
import co.rahulchowdhury.memories.data.model.remote.RandomUsers
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUsersApiService {

    @GET("/api")
    suspend fun fetchRandomUsers(
        @Query("results") limit: Int,
        @Query("page") page: Int,
        @Query("seed") seed: String = Constants.Remote.SEED_KEY,
        @Query("inc") fieldsToInclude: String = Constants.Remote.FIELDS_TO_INCLUDE
    ): RandomUsers

}
