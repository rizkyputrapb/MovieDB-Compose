package com.example.moviedb.api

import com.example.moviedb.domain.model.movie_detail.MovieDetail
import com.example.moviedb.domain.model.responses.GenreMoviesResponse
import com.example.moviedb.domain.model.responses.GenreResponse
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
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("sort_by") sortBy: String = "popularity.desc"
    ) : GenreMoviesResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id") movieId : String) : MovieDetail
}