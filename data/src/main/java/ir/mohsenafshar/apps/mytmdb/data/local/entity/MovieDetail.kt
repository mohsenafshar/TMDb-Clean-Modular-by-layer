package ir.mohsenafshar.apps.mytmdb.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "movie_detail")
data class MovieDetail(
    val id: Long,
    val adult: Boolean,
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String,
    @ColumnInfo(name = "belongs_to_collection")
    val belongsToCollection: Any,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val imdb_id: String,
    val original_language: String,
    @ColumnInfo(name = "original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @ColumnInfo(name = "poster_path")
    val posterPath: String,
    @ColumnInfo(name = "production_companies")
    val productionCompanies: List<ProductionCompany>,
    @ColumnInfo(name = "production_countries")
    val productionCountries: List<ProductionCountry>,
    @ColumnInfo(name = "release_date")
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    @ColumnInfo(name = "spoken_languages")
    val spokenLanguages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double,
    @ColumnInfo(name = "vote_count")
    val voteCount: Int
)