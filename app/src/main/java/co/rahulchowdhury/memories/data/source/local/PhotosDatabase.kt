package co.rahulchowdhury.memories.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import co.rahulchowdhury.memories.data.model.local.Photo

@Database(entities = [Photo::class], version = 1)
abstract class PhotosDatabase : RoomDatabase() {

    abstract fun photosDao(): PhotosDao

}
