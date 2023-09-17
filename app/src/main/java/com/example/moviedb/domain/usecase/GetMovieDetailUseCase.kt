package com.example.moviedb.domain.usecase

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.moviedb.common.Resource
import com.example.moviedb.domain.model.movie_detail.MovieDetail
import com.example.moviedb.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetMovieDetailUseCase@Inject constructor(
    private val repository: Repository,
)  {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    operator fun invoke(movieId: String): Flow<Resource<MovieDetail>> = flow {
        try {
            val response = repository.getMovieDetail(movieId)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    e.localizedMessage ?: "Couldn't reach server. Check your internet connection"
                )
            )
        }
    }
}