package com.eungpang.movienight.ui

import com.eungpang.movienight.BuildConfig.API_KEY
import com.eungpang.movienight.api.MovieApi
import com.eungpang.movienight.entity.Movie
import com.eungpang.movienight.entity.MovieList
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieManager @Inject constructor(private val movieApi : MovieApi) {
    fun getMovieListWithRxJava(page: String): Observable<MovieList> {
        return Observable.create { subscriber ->
            val response = movieApi.getMovieListRetrofit(
                mapOf(
                    "page" to page,
                    "api_key" to API_KEY,
                    "sort_by" to "popularity.desc",
                    "language" to "ko"
                )
            ).execute()

            if (response.isSuccessful) {
                val movieListResults = response.body()?.results?.map {
                    Movie(
                        it.vote_count,
                        it.vote_average,
                        it.title,
                        it.release_date,
                        it.poster_path,
                        it.overview
                    )
                }
                if (movieListResults != null) {
                    val responsePage = response.body()?.page?.plus(1)
                    val movieList = MovieList(responsePage, movieListResults)

                    subscriber.onNext(movieList)
                }
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }

    suspend fun getMovieListWithCoroutine(param: Map<String, String>): MovieList {
        val response = movieApi.getMovieListRetrofitWithCoroutine(param)
        val listOfMovie = response.results.map {
            Movie(
                it.vote_count,
                it.vote_average,
                it.title,
                it.release_date,
                it.poster_path,
                it.overview
            )
        }
        return MovieList(response.page, listOfMovie)
    }
}