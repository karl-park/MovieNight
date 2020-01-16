package com.eungpang.movienight.api

import com.eungpang.movienight.entity.MovieListResponse
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
            .build()
        movieService = retrofit.create(MovieService::class.java)
    }

    fun getMovieListRetrofit(param: Map<String, String>): Call<MovieListResponse> {
        return movieService.getTop(param)
    }

    companion object {
        private val INSTANCE = RetrofitClient()
        fun getClient() = INSTANCE
    }
}