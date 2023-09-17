package com.example.moviedb.domain.model.responses

import com.example.moviedb.domain.model.movie.Movie

data class PopularMovieResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)