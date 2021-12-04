package ir.mohsenafshar.apps.mytmdb.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "production_country")
data class ProductionCountry(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val iso_3166_1: String,
    val name: String
)