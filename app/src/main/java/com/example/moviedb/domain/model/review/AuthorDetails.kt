package com.example.moviedb.domain.model.review

data class AuthorDetails(
    val avatar_path: String,
    val name: String,
    val rating: Int,
    val username: String
)