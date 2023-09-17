package com.example.moviedb.presentation.homepage.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedb.common.Resource
import com.example.moviedb.domain.usecase.GetGenresUseCase
import com.example.moviedb.domain.usecase.GetPopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getGenresUseCase: GetGenresUseCase
) : ViewModel() {

    private val _popularMovieState = mutableStateOf(PopularMoviesState())
    val popularMovieState: State<PopularMoviesState> = _popularMovieState

    private val _GenresState = mutableStateOf(GenresState())
    val genresState: State<GenresState> = _GenresState

    init {
        getPopularMovies()
        getMovieGenres()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    private fun getPopularMovies() {
        getPopularMoviesUseCase().onEach {
            when (it) {
                is Resource.Success -> {
                    _popularMovieState.value =
                        PopularMoviesState(popularMovies = it.data?.results ?: emptyList())
                }

                is Resource.Error -> {
                    Log.e("NetworkError", it.message ?: "An unexpected error occurred")
                    _popularMovieState.value =
                        PopularMoviesState(error = it.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getMovieGenres() {
        getGenresUseCase().onEach {
            when (it) {
                is Resource.Success -> {
                    _GenresState.value =
                        GenresState(genres = it.data?.genres ?: emptyList())
                }

                is Resource.Error -> {
                    Log.e("NetworkError", it.message ?: "An unexpected error occurred")
                    _GenresState.value =
                        GenresState(error = it.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }
}