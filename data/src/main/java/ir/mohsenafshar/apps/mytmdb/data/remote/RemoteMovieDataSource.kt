package ir.mohsenafshar.apps.mytmdb.data.remote

import ir.mohsenafshar.apps.mytmdb.data.IMovieDataSource
import ir.mohsenafshar.apps.mytmdb.data.remote.route.MovieApi
import ir.mohsenafshar.apps.mytmdb.domain.interactor.GetMovieListUseCase
import ir.mohsenafshar.apps.mytmdb.domain.model.MovieDetail
import ir.mohsenafshar.apps.mytmdb.domain.model.MovieItem
import javax.inject.Inject

class RemoteMovieDataSource @Inject constructor(private val movieApi: MovieApi): IMovieDataSource {
    override suspend fun getMovieList(params: GetMovieListUseCase.Params): List<MovieItem> {
        return movieApi.discoverLatestMovies(params.pageNo, params.dateFilter).movieList
    }

    override suspend fun getTopRated(pageNo: Int): List<MovieItem> {
        return movieApi.getTopRated(pageNo).movieList
    }

    override suspend fun getDetail(id: Long): MovieDetail {
        return movieApi.getDetail(id)
    }

    override suspend fun saveAll(movieList: List<MovieItem>) {
        TODO("Not yet implemented")
    }
}