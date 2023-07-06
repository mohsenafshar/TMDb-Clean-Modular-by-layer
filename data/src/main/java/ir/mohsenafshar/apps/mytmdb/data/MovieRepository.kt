package ir.mohsenafshar.apps.mytmdb.data

import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import ir.mohsenafshar.apps.mytmdb.data.di.IODispatcher
import ir.mohsenafshar.apps.mytmdb.data.di.QLocalMovieDataSource
import ir.mohsenafshar.apps.mytmdb.data.di.QRemoteMovieDataSource
import ir.mohsenafshar.apps.mytmdb.data.work.SyncWorker
import ir.mohsenafshar.apps.mytmdb.domain.interactor.GetMovieListUseCase
import ir.mohsenafshar.apps.mytmdb.domain.model.MovieDetail
import ir.mohsenafshar.apps.mytmdb.domain.model.MovieItem
import ir.mohsenafshar.apps.mytmdb.domain.repo.IMovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepository @Inject
constructor(
    @QRemoteMovieDataSource private val remoteMovieDataSource: IMovieDataSource,
    @QLocalMovieDataSource private val localMovieDataSource: IMovieDataSource,
    private val workManager: WorkManager,
    @IODispatcher private val dispatcher: CoroutineDispatcher) : IMovieRepository {
    override suspend fun getMovieList(params: GetMovieListUseCase.Params): Flow<Result<List<MovieItem>>> {


        workManager.enqueue(OneTimeWorkRequest.from(SyncWorker::class.java))
        return localMovieDataSource.getMovieList(params).map {
            Result.success(it)
        }.catch {
            emit(Result.failure(it))
        }
//            val data = withContext(dispatcher) {
//                return@withContext try {
//                    val data = remoteMovieDataSource.getMovieList(params)
//                    localMovieDataSource.saveAll(data)
//                    Result.success(data)
//                } catch (e: Exception) {
//                    Result.failure(e)
//                }
//            }
//            send(data)

    }

    override suspend fun getTopRated(pageNo: Int): Flow<Result<List<MovieItem>>> {
        return merge(localMovieDataSource.getTopRated(pageNo), remoteMovieDataSource.getTopRated(pageNo))
            .map {
                Result.success(it)
            }
            .catch {
                emit(Result.failure(it))
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