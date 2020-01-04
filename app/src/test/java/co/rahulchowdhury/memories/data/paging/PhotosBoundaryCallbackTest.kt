package co.rahulchowdhury.memories.data.paging

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import co.rahulchowdhury.memories.data.Constants
import co.rahulchowdhury.memories.data.model.local.Photo
import co.rahulchowdhury.memories.data.model.remote.*
import co.rahulchowdhury.memories.data.source.local.PhotosLocalSource
import co.rahulchowdhury.memories.data.source.remote.PhotosRemoteSource
import co.rahulchowdhury.memories.data.util.LiveDataTestUtil.getValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class PhotosBoundaryCallbackTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var photosLocalSource: PhotosLocalSource
    @Mock
    private lateinit var photosRemoteSource: PhotosRemoteSource

    private lateinit var photosBoundaryCallback: PhotosBoundaryCallback

    @Test
    fun `should fetch and save photos onZeroItemsLoaded from DB`() {
        runBlocking {
            val limit = Constants.Remote.PAGE_SIZE
            val page = 1

            val remotePhotos = listOf(
                RemotePhoto(
                    Login(UUID.randomUUID().toString()),
                    Picture("", "", "")
                )
            )

            photosBoundaryCallback = PhotosBoundaryCallback(
                this,
                photosLocalSource,
                photosRemoteSource
            )

            `when`(photosRemoteSource.fetchPhotos(limit, page)).thenReturn(remotePhotos)

            photosBoundaryCallback.onZeroItemsLoaded()

            delay(1000)

            verify(photosRemoteSource).fetchPhotos(
                limit,
                page
            )

            verify(photosLocalSource).save(anyList())

            val networkResponseState = getValue(photosBoundaryCallback.networkResponseState)
            assertThat(networkResponseState).isInstanceOf(Success::class.java)

            verifyNoMoreInteractions(photosRemoteSource)
            verifyNoMoreInteractions(photosLocalSource)
        }
    }

    @Test
    fun `should fetch and save photos onItemAtEndLoaded from DB`() {
        runBlocking {
            val limit = Constants.Remote.PAGE_SIZE
            val page = 1

            val remotePhotos = listOf(
                RemotePhoto(
                    Login(UUID.randomUUID().toString()),
                    Picture("", "", "")
                )
            )

            photosBoundaryCallback = PhotosBoundaryCallback(
                this,
                photosLocalSource,
                photosRemoteSource
            )

            `when`(photosRemoteSource.fetchPhotos(limit, page)).thenReturn(remotePhotos)

            photosBoundaryCallback.onItemAtEndLoaded(
                Photo(
                    uuid = "",
                    originalUrl = ""
                )
            )

            delay(1000)

            verify(photosRemoteSource).fetchPhotos(
                limit,
                page
            )

            verify(photosLocalSource).save(anyList())

            val networkResponseState = getValue(photosBoundaryCallback.networkResponseState)
            assertThat(networkResponseState).isInstanceOf(Success::class.java)

            verifyNoMoreInteractions(photosRemoteSource)
            verifyNoMoreInteractions(photosLocalSource)
        }
    }

    @Test
    fun `should quietly fail and update network response state for no internet`() {
        runBlocking {
            val limit = Constants.Remote.PAGE_SIZE
            val page = 1


            photosBoundaryCallback = PhotosBoundaryCallback(
                this,
                photosLocalSource,
                photosRemoteSource
            )

            given(photosRemoteSource.fetchPhotos(limit, page)).willAnswer {
                throw UnknownHostException()
            }

            photosBoundaryCallback.onItemAtEndLoaded(
                Photo(
                    uuid = "",
                    originalUrl = ""
                )
            )

            delay(1000)

            verify(photosRemoteSource).fetchPhotos(
                limit,
                page
            )

            val networkResponseState = getValue(photosBoundaryCallback.networkResponseState)
            assertThat(networkResponseState).isInstanceOf(Error::class.java)

            verifyNoMoreInteractions(photosRemoteSource)
            verifyZeroInteractions(photosLocalSource)
        }
    }

    @Test
    fun `should quietly fail and update network response state for server error`() {
        runBlocking {
            val limit = Constants.Remote.PAGE_SIZE
            val page = 1


            photosBoundaryCallback = PhotosBoundaryCallback(
                this,
                photosLocalSource,
                photosRemoteSource
            )

            given(photosRemoteSource.fetchPhotos(limit, page)).willAnswer {
                throw HttpException(
                    Response.error<RemotePhoto>(
                        502,
                        ResponseBody.create(null, "")
                    )
                )
            }

            photosBoundaryCallback.onItemAtEndLoaded(
                Photo(
                    uuid = "",
                    originalUrl = ""
                )
            )

            delay(1000)

            verify(photosRemoteSource).fetchPhotos(
                limit,
                page
            )

            val networkResponseState = getValue(photosBoundaryCallback.networkResponseState)
            assertThat(networkResponseState).isInstanceOf(Error::class.java)

            verifyNoMoreInteractions(photosRemoteSource)
            verifyZeroInteractions(photosLocalSource)
        }
    }

}
