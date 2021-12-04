package ir.mohsenafshar.apps.mytmdb.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Genre(
    @PrimaryKey
    val id: Long,
    val name: String
)