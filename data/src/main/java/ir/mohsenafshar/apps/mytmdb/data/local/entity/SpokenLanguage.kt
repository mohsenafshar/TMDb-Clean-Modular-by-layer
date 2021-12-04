package ir.mohsenafshar.apps.mytmdb.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "spoken_language")
data class SpokenLanguage(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val english_name: String,
    val iso_639_1: String,
    val name: String
)