package com.eungpang.movienight.ui

import com.eungpang.movienight.BuildConfig.API_KEY
import com.eungpang.movienight.api.RetrofitClient
import com.eungpang.movienight.entity.Movie
import io.reactivex.Observable

class MovieManager {
    fun getMovieList(): Observable<List<Movie>> {
        return Observable.create { subscriber ->
            val response = RetrofitClient.getClient().getMovieListRetrofit(
                mapOf(
                    "page" to "1",
                    "api_key" to API_KEY,
                    "sort_by" to "popularity.desc",
                    "language" to "ko"
                )
            ).execute()

            if (response.isSuccessful) {
                val movieList = response.body()?.results?.map {
                    Movie(
                        it.vote_count,
                        it.vote_average,
                        it.title,
                        it.release_date,
                        it.poster_path,
                        it.overview
                    )
                }
                if (movieList != null) {
                    subscriber.onNext(movieList)
                }
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }
}