package ir.mohsenafshar.apps.mytmdb.data.remote.route

import ir.mohsenafshar.apps.mytmdb.data.remote.model.MovieListResponse
import ir.mohsenafshar.apps.mytmdb.domain.model.MovieDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/top_rated")
    suspend fun getTopRated(@Query("page") pageNo: Int): MovieListResponse

    @GET("movie/{id}")
    suspend fun getDetail(@Path("id") id: Long): MovieDetail

    @GET("discover/movie?sort_by=release_date.desc")
    suspend fun discoverLatestMovies(
        @Query("page") pageNo: Int,
        @Query("release_date.lte") maxReleaseDate: String?
    ): MovieListResponse
}