package com.example.moviedb.domain.model.responses

import com.example.moviedb.domain.model.genre.Genre

data class GenreResponse(
    val genres: List<Genre>
)