package com.example.moviedb.domain.model.responses

import com.example.moviedb.domain.model.video.MovieVideo

data class MovieVideoResponse(
    val id: Int,
    val results: List<MovieVideo>
)