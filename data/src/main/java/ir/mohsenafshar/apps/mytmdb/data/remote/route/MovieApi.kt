package ir.mohsenafshar.apps.mytmdb.data.remote.route

import ir.mohsenafshar.apps.mytmdb.data.remote.model.MovieListResponse
import ir.mohsenafshar.apps.mytmdb.domain.model.MovieDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/{id}")
    suspend fun getDetail(@Path("id") id: Long): MovieDetail

    @GET("movie/top_rated?language=en-US")
    suspend fun getTopRated(@Query("page") pageNo: Int): MovieListResponse

    @GET("movie/popular?language=en-US")
    suspend fun popularMovies(@Query("page") pageNo: Int): MovieListResponse

    @GET("movie/upcoming?language=en-US")
    suspend fun upcomingMovies(@Query("page") pageNo: Int): MovieListResponse

    @GET("discover/movie?sort_by=release_date.desc")
    suspend fun discoverLatestMovies(
        @Query("page") pageNo: Int,
        @Query("release_date.lte") maxReleaseDate: String?
    ): MovieListResponse
}