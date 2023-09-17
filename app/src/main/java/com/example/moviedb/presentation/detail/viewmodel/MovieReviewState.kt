package com.example.moviedb.presentation.detail.viewmodel

import com.example.moviedb.domain.model.review.Review

data class MovieReviewState(
    val reviews: List<Review>? = emptyList(),
    val error: String = ""
)
