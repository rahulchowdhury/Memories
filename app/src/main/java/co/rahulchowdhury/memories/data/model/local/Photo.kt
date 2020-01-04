package co.rahulchowdhury.memories.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class Photo(
    @PrimaryKey
    val uuid: String,
    val originalUrl: String
)
