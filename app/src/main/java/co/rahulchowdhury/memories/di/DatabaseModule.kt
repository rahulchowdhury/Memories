package co.rahulchowdhury.memories.di

import android.app.Application
import androidx.room.Room
import co.rahulchowdhury.memories.data.source.local.PhotosDao
import co.rahulchowdhury.memories.data.source.local.PhotosDatabase
import org.koin.dsl.module

val databaseModule = module {
    single { providePhotosDao(get()) }
}

private fun providePhotosDao(
    application: Application
): PhotosDao =
    Room.databaseBuilder(
        application,
        PhotosDatabase::class.java,
        "photos.db"
    ).build().photosDao()

