package ir.mohsenafshar.apps.mytmdb.domain.model

import com.google.gson.annotations.SerializedName
import kotlin.random.Random

data class MovieItem(
    val id: Long,
    val adult: Boolean?,
    val backdrop_path: String?,
    val genre_ids: List<Long>?,
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
) {
    val imageUrl : String by lazy {
        val index = Random.nextInt(0, urls.size)
        urls[index]
    }
}

val urls = listOf(
    "https://img.freepik.com/premium-photo/girl-with-lightning-bolt-her-face_888396-1518.jpg",
    "https://img.freepik.com/premium-photo/woman-with-lightning-bolt-her-head_888396-1706.jpg",
    "https://img.freepik.com/premium-photo/woman-with-lightning-bolt-her-face_888396-1711.jpg?w=200",
    "https://img.freepik.com/premium-photo/woman-with-blonde-hair-glowing-face_888396-1575.jpg?w=200",
    "https://img.freepik.com/premium-photo/woman-with-blonde-hair-lightning-bolt-her-head_888396-1567.jpg?w=200",
)