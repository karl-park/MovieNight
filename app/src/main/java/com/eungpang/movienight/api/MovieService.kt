package com.eungpang.movienight.api

import com.eungpang.movienight.entity.MovieListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface MovieService {

    @GET("discover/movie")
    fun getTop(@QueryMap param: Map<String, String>): Call<MovieListResponse>

}