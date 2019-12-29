package co.rahulchowdhury.memories.di

import co.rahulchowdhury.memories.data.repo.DefaultPhotosRepository
import co.rahulchowdhury.memories.data.repo.PhotosRepository
import co.rahulchowdhury.memories.data.source.local.DefaultPhotosLocalSource
import co.rahulchowdhury.memories.data.source.local.PhotosDao
import co.rahulchowdhury.memories.data.source.local.PhotosLocalSource
import co.rahulchowdhury.memories.data.source.remote.PhotosRemoteSource
import co.rahulchowdhury.memories.data.source.remote.RandomPhotosRemoteSource
import co.rahulchowdhury.memories.data.source.remote.RandomUsersApiService
import co.rahulchowdhury.memories.ui.GalleryViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val galleryModule = module {
    single { providePhotosLocalSource(get()) }
    single { providePhotosRemoteSource(get()) }
    single { providePhotosRepository(get(), get()) }
    viewModel { provideGalleryViewModel(get()) }
}

private fun providePhotosLocalSource(
    photosDao: PhotosDao
): PhotosLocalSource = DefaultPhotosLocalSource(photosDao)

private fun providePhotosRemoteSource(
    randomUsersApiService: RandomUsersApiService
): PhotosRemoteSource = RandomPhotosRemoteSource(randomUsersApiService)

private fun providePhotosRepository(
    photosLocalSource: PhotosLocalSource,
    photosRemoteSource: PhotosRemoteSource
): PhotosRepository = DefaultPhotosRepository(photosLocalSource, photosRemoteSource)

private fun provideGalleryViewModel(
    photosRepository: PhotosRepository
): GalleryViewModel = GalleryViewModel(photosRepository)
