package com.example.moviedb.di

import android.util.Log
import com.example.moviedb.BuildConfig
import com.example.moviedb.api.NetworkService
import com.example.moviedb.domain.repository.Repository
import com.example.moviedb.domain.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
        .setLevel(HttpLoggingInterceptor.Level.HEADERS);
    private val client = OkHttpClient.Builder()
        .addInterceptor(HeaderInterceptor())
        .addInterceptor(logging)
        .build()

    @Provides
    @Singleton
    fun provideNetworkService(): NetworkService {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(NetworkService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(api: NetworkService): Repository {
        return RepositoryImpl(api)
    }
}

class HeaderInterceptor : Interceptor {
    private val apiKey = BuildConfig.MOVIEDB_API_KEY

    /**
     * Interceptor class for setting of the dynamic headers for every request
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d("APIKEY", apiKey)
        var request = chain.request()
        request = request.newBuilder()
            .addHeader("Authorization", "Bearer $apiKey")
            .build()
        return chain.proceed(request)
    }
}