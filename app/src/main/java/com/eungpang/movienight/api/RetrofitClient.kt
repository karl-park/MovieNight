package com.eungpang.movienight.api

import com.eungpang.movienight.entity.MovieListResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.themoviedb.org/3/"

class RetrofitClient {
    private val movieService: MovieService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
        movieService = retrofit.create(MovieService::class.java)
    }

    fun getMovieListRetrofit(param: Map<String, String>): Call<MovieListResponse> {
        return movieService.getTop(param)
    }

    suspend fun getMovieListRetrofitWithCoroutine(param: Map<String, String>): MovieListResponse {
        return movieService.getDeferredTop(param).await()
    }

    companion object {
        private val INSTANCE = RetrofitClient()
        fun getClient() = INSTANCE
    }
}