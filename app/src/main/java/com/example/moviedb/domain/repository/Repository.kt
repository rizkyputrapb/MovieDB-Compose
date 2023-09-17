package com.example.moviedb.domain.repository

import com.example.moviedb.api.NetworkService
import com.example.moviedb.domain.model.GenreMoviesResponse
import com.example.moviedb.domain.model.GenreResponse
import com.example.moviedb.domain.model.PopularMovieResponse
import javax.inject.Inject

interface Repository {

    suspend fun getPopularMovies(): PopularMovieResponse
    suspend fun getGenres(): GenreResponse
    suspend fun getMoviesByGenre(genre: String): GenreMoviesResponse
}

class RepositoryImpl @Inject constructor(private val networkService: NetworkService) : Repository {
    override suspend fun getPopularMovies(): PopularMovieResponse {
        return networkService.getPopularMovies()
    }

    override suspend fun getGenres(): GenreResponse {
        return networkService.getGenres()
    }

    override suspend fun getMoviesByGenre(genre: String): GenreMoviesResponse {
        return networkService.getMovieByGenre(genre = genre)
    }
}