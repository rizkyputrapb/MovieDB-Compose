package com.example.moviedb.presentation.detail.viewmodel

import com.example.moviedb.domain.model.video.MovieVideo

data class MovieVideoState(
    val video: MovieVideo? = null,
    val error: String = ""
)