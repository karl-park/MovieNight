package com.eungpang.movienight

import android.app.Application
import com.eungpang.movienight.di.DaggerMovieComponent
import com.eungpang.movienight.di.MovieComponent

class MyApplication : Application() {
    companion object {
        lateinit var movieComponent: MovieComponent
    }

    override fun onCreate() {
        super.onCreate()
        movieComponent = DaggerMovieComponent.builder().build()
    }
}
