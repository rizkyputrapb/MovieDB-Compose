package com.example.moviedb.presentation.genre.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedb.common.Resource
import com.example.moviedb.domain.usecase.GetMovieByGenreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
    private val getMovieByGenreUseCase: GetMovieByGenreUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _moviesState = mutableStateOf(GenreMoviesState())
    val moviesState: State<GenreMoviesState> = _moviesState

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getMoviesByGenre(genreId: String, page: Int?) {
        getMovieByGenreUseCase(genreId, page).onEach {
            when (it) {
                is Resource.Success -> {
                    _moviesState.value =
                        GenreMoviesState(movies = it.data?.results ?: emptyList())
                }

                is Resource.Error -> {
                    Log.e("NetworkError", it.message ?: "An unexpected error occurred")
                    _moviesState.value =
                        GenreMoviesState(error = it.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }
}