package ir.mohsenafshar.apps.mytmdb.data

import ir.mohsenafshar.apps.mytmdb.data.di.IODispatcher
import ir.mohsenafshar.apps.mytmdb.domain.interactor.GetMovieListUseCase
import ir.mohsenafshar.apps.mytmdb.domain.model.MovieDetail
import ir.mohsenafshar.apps.mytmdb.domain.model.MovieItem
import ir.mohsenafshar.apps.mytmdb.domain.repo.IMovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class MovieRepository @Inject constructor(private val remoteMovieDataSource: IMovieDataSource, @IODispatcher private val dispatcher: CoroutineDispatcher) : IMovieRepository {
    override suspend fun getMovieList(params: GetMovieListUseCase.Params): Result<List<MovieItem>> {
        return withContext(dispatcher) {
            return@withContext try {
                Result.success(remoteMovieDataSource.getMovieList(params))
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun getTopRated(pageNo: Int): Result<List<MovieItem>> {
        return withContext(dispatcher) {
            return@withContext try {
                Result.success(remoteMovieDataSource.getTopRated(pageNo))
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun getDetail(id: Long): Result<MovieDetail> {
        return withContext(dispatcher) {
            return@withContext try {
                Result.success(remoteMovieDataSource.getDetail(id))
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}