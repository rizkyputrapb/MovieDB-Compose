package com.example.moviedb.presentation.detail.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedb.common.Resource
import com.example.moviedb.domain.usecase.GetMovieDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase
) : ViewModel() {
    private val _movieDetailState = mutableStateOf(MovieDetailState())
    val movieDetailState: State<MovieDetailState> = _movieDetailState

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getMoviesByGenre(movieId: String) {
        getMovieDetailUseCase(movieId).onEach {
            when (it) {
                is Resource.Success -> {
                    _movieDetailState.value =
                        MovieDetailState(movies = it.data)
                }

                is Resource.Error -> {
                    Log.e("NetworkError", it.message ?: "An unexpected error occurred")
                    _movieDetailState.value =
                        MovieDetailState(error = it.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }
}