package ir.mohsenafshar.apps.mytmdb.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_item")
data class MovieItem(
    @PrimaryKey
    val id: Long,
    val adult: Boolean?,
    val backdrop_path: String?,
    val genres: List<Long>?,
    val original_language: String?,
    val original_title: String?,
    val overview: String,
    val popularity: Double?,
    val poster_path: String?,
    val releaseDate: String,
    val title: String,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?
)