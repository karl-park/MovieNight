package com.eungpang.movienight.ui

import com.eungpang.movienight.entity.Movie
import io.reactivex.Observable

class MovieManager {
    fun getMovieList(): Observable<List<Movie>> {
        return Observable.create { subscriber ->
            val movieList = mutableListOf<Movie>()
            for (i in 1..10) {
                movieList.apply {
                    add(
                        Movie(
                            1234,
                            5.0f,
                            "Movie ${(i + 0)}",
                            "2020-$i-${i + 0}",
                            "https://picsum.photos/480/640?image=$i",
                            "Movie${i + 0}"
                        )
                    )

                    add(
                        Movie(
                            1234,
                            5.0f,
                            "Movie ${i + 1}",
                            "2019-$i-${i + 1}",
                            "https://picsum.photos/480/640?image=$i",
                            "Movie${i + 1}"
                        )
                    )

                    add(
                        Movie(
                            1234,
                            5.0f,
                            "Movie ${i + 2}",
                            "2018-$i-${i + 2}",
                            "https://picsum.photos/480/640?image=$i",
                            "Movie${i + 2}"
                        )
                    )
                }
            }

            subscriber.onNext(movieList)
            subscriber.onComplete()
        }
    }
}