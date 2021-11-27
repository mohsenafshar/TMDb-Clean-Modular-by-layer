package ir.mohsenafshar.apps.mytmdb.domain.model

import com.google.gson.annotations.SerializedName

data class MovieItem(
    val adult: Boolean?,
    val backdrop_path: String?,
    val genre_ids: List<Int>?,
    val id: Long,
    val original_language: String?,
    val original_title: String?,
    val overview: String,
    val popularity: Double?,
    val poster_path: String?,
    @SerializedName("release_date")
    val releaseDate: String,
    val title: String,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?
)