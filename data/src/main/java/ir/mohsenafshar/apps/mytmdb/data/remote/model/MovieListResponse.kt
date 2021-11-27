package ir.mohsenafshar.apps.mytmdb.data.remote.model

import com.google.gson.annotations.SerializedName
import ir.mohsenafshar.apps.mytmdb.domain.model.MovieItem

data class MovieListResponse(
    @SerializedName("results")
    val movieList: List<MovieItem>
)
