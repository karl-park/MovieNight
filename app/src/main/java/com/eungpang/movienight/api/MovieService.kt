package com.eungpang.movienight.api

import com.eungpang.movienight.entity.MovieListResponse
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface MovieService {

    @GET("discover/movie")
    fun getTop(@QueryMap param: Map<String, String>): Call<MovieListResponse>

    @GET("discover/movie")
    fun getTopRx(@QueryMap param: Map<String, String>): Observable<MovieListResponse>

    @GET("discover/movie")
    fun getDeferredTop(@QueryMap param: Map<String, String>): Deferred<MovieListResponse>

}