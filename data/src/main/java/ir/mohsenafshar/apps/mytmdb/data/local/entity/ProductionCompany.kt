package ir.mohsenafshar.apps.mytmdb.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductionCompany(
    @PrimaryKey
    val id: Long,
    val logo_path: String,
    val name: String,
    val origin_country: String
)