package ir.mohsenafshar.apps.mytmdb.data

import ir.mohsenafshar.apps.mytmdb.data.di.IODispatcher
import ir.mohsenafshar.apps.mytmdb.data.di.QLocalMovieDataSource
import ir.mohsenafshar.apps.mytmdb.data.di.QRemoteMovieDataSource
import ir.mohsenafshar.apps.mytmdb.domain.interactor.GetMovieListUseCase
import ir.mohsenafshar.apps.mytmdb.domain.model.MovieDetail
import ir.mohsenafshar.apps.mytmdb.domain.model.MovieItem
import ir.mohsenafshar.apps.mytmdb.domain.repo.IMovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepository @Inject
constructor(
    @QRemoteMovieDataSource private val remoteMovieDataSource: IMovieDataSource,
    @QLocalMovieDataSource private val localMovieDataSource: IMovieDataSource,
    @IODispatcher private val dispatcher: CoroutineDispatcher) : IMovieRepository {
    override suspend fun getMovieList(params: GetMovieListUseCase.Params): Flow<Result<List<MovieItem>>> {
        return channelFlow {
            val localData = withContext(dispatcher) {
                return@withContext try {
                    val data = localMovieDataSource.getMovieList(params)
                    Result.success(data)
                } catch (e: Exception) {
                    Result.failure(e)
                }
            }
            send(localData)
            val data = withContext(dispatcher) {
                return@withContext try {
                    val data = remoteMovieDataSource.getMovieList(params)
                    localMovieDataSource.saveAll(data)
                    Result.success(data)
                } catch (e: Exception) {
                    Result.failure(e)
                }
            }
            send(data)
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