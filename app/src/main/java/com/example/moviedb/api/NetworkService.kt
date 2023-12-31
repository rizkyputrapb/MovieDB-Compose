package com.example.moviedb.api

import com.example.moviedb.domain.model.movie_detail.MovieDetail
import com.example.moviedb.domain.model.responses.GenreMoviesResponse
import com.example.moviedb.domain.model.responses.GenreResponse
import com.example.moviedb.domain.model.responses.MovieReviewResponse
import com.example.moviedb.domain.model.responses.MovieVideoResponse
import com.example.moviedb.domain.model.responses.PopularMovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {
    @GET("movie/popular")
    suspend fun getPopularMovies(): PopularMovieResponse

    @GET("genre/movie/list")
    suspend fun getGenres(): GenreResponse

    @GET("discover/movie")
    suspend fun getMovieByGenre(
        @Query("with_genres") genre: String?,
        @Query("include_adult") includeAdult: Boolean? = false,
        @Query("include_video") includeVideo: Boolean? = false,
        @Query("language") language: String? = "en-US",
        @Query("page") page: Int? = 1,
        @Query("sort_by") sortBy: String? = "popularity.desc"
    ): GenreMoviesResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: String,
    ): MovieDetail

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: String,
    ): MovieVideoResponse

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: String,
        @Query("page") page: Int? = 1,
    ): MovieReviewResponse
}