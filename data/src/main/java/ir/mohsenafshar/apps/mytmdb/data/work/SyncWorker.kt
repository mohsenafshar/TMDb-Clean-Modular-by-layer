package ir.mohsenafshar.apps.mytmdb.data.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import ir.mohsenafshar.apps.mytmdb.data.IMovieDataSource
import ir.mohsenafshar.apps.mytmdb.data.di.QLocalMovieDataSource
import ir.mohsenafshar.apps.mytmdb.data.di.QRemoteMovieDataSource
import ir.mohsenafshar.apps.mytmdb.domain.interactor.GetMovieListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    @QRemoteMovieDataSource private val remoteMovieDataSource: IMovieDataSource,
    @QLocalMovieDataSource private val localMovieDataSource: IMovieDataSource,
) : CoroutineWorker(
    context, workerParameters
) {
    override suspend fun doWork(): Result {
        return try {
            withContext(Dispatchers.IO) {
                remoteMovieDataSource.getMovieList(GetMovieListUseCase.Params(1, "")).collect {
                    localMovieDataSource.saveAll(it)
                }
            }
            Result.success()
        } catch (e: Exception) {
            println(e)
            Result.failure()
        }
    }
}