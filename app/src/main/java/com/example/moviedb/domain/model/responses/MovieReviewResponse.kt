package com.example.moviedb.domain.model.responses

import com.example.moviedb.domain.model.review.Review

data class MovieReviewResponse(
    val id: Int,
    val page: Int,
    val results: List<Review>,
    val total_pages: Int,
    val total_results: Int
)