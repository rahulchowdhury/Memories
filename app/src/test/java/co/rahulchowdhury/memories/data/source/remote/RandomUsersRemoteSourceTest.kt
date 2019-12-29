package co.rahulchowdhury.memories.data.source.remote

import co.rahulchowdhury.memories.data.model.remote.Login
import co.rahulchowdhury.memories.data.model.remote.Picture
import co.rahulchowdhury.memories.data.model.remote.RandomUsers
import co.rahulchowdhury.memories.data.model.remote.User
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class RandomUsersRemoteSourceTest {

    @Mock
    private lateinit var randomUsersApiService: RandomUsersApiService

    private lateinit var randomUsersRemoteSource: RandomUsersRemoteSource

    @Before
    fun setUp() {
        randomUsersRemoteSource = RandomUsersRemoteSource(randomUsersApiService)
    }

    @Test
    fun `should return a list of random users from the api`() {
        val seedRandomUsers = RandomUsers(
            results = listOf(
                User(
                    email = "rahul@rahul.com",
                    login = Login(uuid = UUID.randomUUID().toString()),
                    picture = Picture(
                        large = "large.jpg",
                        medium = "medium.jpg",
                        thumbnail = "thumbnail.jpg"
                    )
                )
            )
        )
        val limit = 1
        val page = 1

        runBlocking {
            `when`(randomUsersApiService.fetchRandomUsers(limit, page)).thenReturn(seedRandomUsers)

            val users = randomUsersRemoteSource.fetchUsers(limit, page)

            assertThat(users).isEqualTo((seedRandomUsers.results))

            verify(randomUsersApiService).fetchRandomUsers(limit, page)
            verifyNoMoreInteractions(randomUsersApiService)
        }
    }

    @Test(expected = RuntimeException::class)
    fun `should throw exception for api error`() {
        val limit = 1
        val page = 1

        runBlocking {
            `when`(randomUsersApiService.fetchRandomUsers(limit, page)).thenThrow(RuntimeException())

            randomUsersRemoteSource.fetchUsers(limit, page)
        }
    }

}
