package ir.mohsenafshar.apps.mytmdb.data

import ir.mohsenafshar.apps.mytmdb.domain.interactor.GetMovieListUseCase
import ir.mohsenafshar.apps.mytmdb.domain.model.MovieDetail
import ir.mohsenafshar.apps.mytmdb.domain.model.MovieItem
import kotlinx.coroutines.flow.Flow

interface IMovieDataSource {
    suspend fun getMovieList(params: GetMovieListUseCase.Params): Flow<List<MovieItem>>
    suspend fun getTopRated(pageNo: Int): Flow<List<MovieItem>>
    suspend fun getDetail(id: Long): MovieDetail
    suspend fun saveAll(movieList: List<MovieItem>)
}