package com.example.moviedb.domain.repository

import com.example.moviedb.api.NetworkService
import com.example.moviedb.domain.model.movie_detail.MovieDetail
import com.example.moviedb.domain.model.responses.GenreMoviesResponse
import com.example.moviedb.domain.model.responses.GenreResponse
import com.example.moviedb.domain.model.responses.MovieReviewResponse
import com.example.moviedb.domain.model.responses.MovieVideoResponse
import com.example.moviedb.domain.model.responses.PopularMovieResponse
import javax.inject.Inject

interface Repository {

    suspend fun getPopularMovies(): PopularMovieResponse
    suspend fun getGenres(): GenreResponse
    suspend fun getMoviesByGenre(genre: String, page: Int?): GenreMoviesResponse
    suspend fun getMovieDetail(movieId: String): MovieDetail
    suspend fun getMovieVideo(movieId: String): MovieVideoResponse
    suspend fun getMovieReviews(movieId: String, page: Int?): MovieReviewResponse
}

class RepositoryImpl @Inject constructor(private val networkService: NetworkService) : Repository {
    override suspend fun getPopularMovies(): PopularMovieResponse {
        return networkService.getPopularMovies()
    }

    override suspend fun getGenres(): GenreResponse {
        return networkService.getGenres()
    }

    override suspend fun getMoviesByGenre(genre: String, page: Int?): GenreMoviesResponse {
        return networkService.getMovieByGenre(genre = genre, page = page)
    }

    override suspend fun getMovieDetail(movieId: String): MovieDetail {
        return networkService.getMovieDetail(movieId = movieId)
    }

    override suspend fun getMovieVideo(movieId: String): MovieVideoResponse {
        return networkService.getMovieVideos(movieId)
    }

    override suspend fun getMovieReviews(movieId: String, page: Int?): MovieReviewResponse {
        return networkService.getMovieReviews(movieId, page)
    }
}