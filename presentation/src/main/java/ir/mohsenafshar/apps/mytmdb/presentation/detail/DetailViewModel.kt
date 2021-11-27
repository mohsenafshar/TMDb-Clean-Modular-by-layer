package ir.mohsenafshar.apps.mytmdb.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.mohsenafshar.apps.mytmdb.domain.interactor.GetDetailMovieUseCase
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(savedStateHandle: SavedStateHandle, private val movieDetailUseCase: GetDetailMovieUseCase) : ViewModel() {

    private val movieId: Long = savedStateHandle.get<Long>(MOVIE_ID_SAVED_STATE_KEY)!!

    val movie = liveData {
        val result = movieDetailUseCase.invoke(GetDetailMovieUseCase.Params(movieId))
        emit(result.getOrThrow())
    }

    companion object {
        private const val MOVIE_ID_SAVED_STATE_KEY = "movieId"
    }
}