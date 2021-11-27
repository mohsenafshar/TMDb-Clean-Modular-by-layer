package ir.mohsenafshar.apps.mytmdb.data

import ir.mohsenafshar.apps.mytmdb.domain.interactor.GetMovieListUseCase
import ir.mohsenafshar.apps.mytmdb.domain.model.MovieDetail
import ir.mohsenafshar.apps.mytmdb.domain.model.MovieItem

interface IMovieDataSource {
    suspend fun getMovieList(params: GetMovieListUseCase.Params): List<MovieItem>
    suspend fun getTopRated(pageNo: Int): List<MovieItem>
    suspend fun getDetail(id: Long): MovieDetail
}