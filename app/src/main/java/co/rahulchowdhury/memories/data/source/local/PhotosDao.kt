package co.rahulchowdhury.memories.data.source.local

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.rahulchowdhury.memories.data.model.local.Photo

@Dao
interface PhotosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(photos: List<Photo>)

    @Query("SELECT * FROM photos")
    fun loadAll(): DataSource.Factory<Int, Photo>

}
