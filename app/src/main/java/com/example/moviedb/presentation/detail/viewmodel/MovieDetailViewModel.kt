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
import com.example.moviedb.domain.usecase.GetMovieReviewsUseCase
import com.example.moviedb.domain.usecase.GetMovieVideoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getMovieVideoUseCase: GetMovieVideoUseCase,
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase
) : ViewModel() {
    private val _movieDetailState = mutableStateOf(MovieDetailState())
    val movieDetailState: State<MovieDetailState> = _movieDetailState

    private val _movieVideoState = mutableStateOf(MovieVideoState())
    val movieVideoState: State<MovieVideoState> = _movieVideoState

    private val _movieReviewState = mutableStateOf(MovieReviewState())
    val movieReviewState: State<MovieReviewState> = _movieReviewState

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

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getMovieVideo(movieId: String) {
        getMovieVideoUseCase(movieId).onEach {
            when (it) {
                is Resource.Success -> {
                    _movieVideoState.value =
                        MovieVideoState(video = it.data?.results?.find { video -> video.type.lowercase() == "trailer" && video.site.lowercase() == "youtube" })
                }

                is Resource.Error -> {
                    Log.e("NetworkError", it.message ?: "An unexpected error occurred")
                    _movieVideoState.value =
                        MovieVideoState(error = it.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getMovieReviews(movieId: String, page: Int?) {
        getMovieReviewsUseCase(movieId, page).onEach {
            when (it) {
                is Resource.Success -> {
                    _movieReviewState.value =
                        MovieReviewState(reviews = it.data?.results)
                }

                is Resource.Error -> {
                    Log.e("NetworkError", it.message ?: "An unexpected error occurred")
                    _movieReviewState.value =
                        MovieReviewState(error = it.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }
}