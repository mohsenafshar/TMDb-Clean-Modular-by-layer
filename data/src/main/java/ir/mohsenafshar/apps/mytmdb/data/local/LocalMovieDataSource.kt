package ir.mohsenafshar.apps.mytmdb.data.local

import ir.mohsenafshar.apps.mytmdb.data.IMovieDataSource
import ir.mohsenafshar.apps.mytmdb.data.Mapper
import ir.mohsenafshar.apps.mytmdb.data.di.IODispatcher
import ir.mohsenafshar.apps.mytmdb.domain.interactor.GetMovieListUseCase
import ir.mohsenafshar.apps.mytmdb.domain.model.MovieDetail
import ir.mohsenafshar.apps.mytmdb.domain.model.MovieItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import timber.log.Timber
import javax.inject.Inject

class LocalMovieDataSource @Inject constructor(private val movieDao: MovieDao, private val mapper: Mapper, @IODispatcher private val ioDispatcher: CoroutineDispatcher) : IMovieDataSource {
    override suspend fun getMovieList(params: GetMovieListUseCase.Params): Flow<List<MovieItem>> {
        return movieDao.getMovieList(params.pageNo)
            .map {
                it.map {
                    mapper.entityToDomain(it)
                }
            }
    }

    override suspend fun getTopRated(pageNo: Int): kotlinx.coroutines.flow.Flow<List<MovieItem>> {
        return movieDao.getMovieList(pageNo).map {
            it.map {
                mapper.entityToDomain(it)
            }
        }.catch {
            println(it)
        }
    }

    override suspend fun getDetail(id: Long): MovieDetail {
        TODO("Not yet implemented")
    }

    override suspend fun saveAll(movieList: List<MovieItem>) {
        val list = movieList.toTypedArray().map {
            mapper.domainToEntity(it)
        }
        movieDao.insertAll(*list.toTypedArray())
    }
}