package com.eungpang.movienight.api

import com.eungpang.movienight.entity.MovieListResponse
import io.reactivex.Observable
import retrofit2.Call
import javax.inject.Inject

class MovieApiImpl @Inject constructor(private val movieService: MovieService)
    : MovieApi {

    override fun getMovieListRx(param: Map<String, String>): Observable<MovieListResponse> =
        movieService.getTopRx(param)

    override fun getMovieListRetrofit(param: Map<String, String>): Call<MovieListResponse> =
        movieService.getTop(param)

    override suspend fun getMovieListRetrofitWithCoroutine(param: Map<String, String>): MovieListResponse =
        movieService.getDeferredTop(param).await()
}


interface MovieApi {
    fun getMovieListRx(param: Map<String, String>): Observable<MovieListResponse>

    fun getMovieListRetrofit(param: Map<String, String>): Call<MovieListResponse>

    suspend fun getMovieListRetrofitWithCoroutine(param: Map<String, String>): MovieListResponse
}
