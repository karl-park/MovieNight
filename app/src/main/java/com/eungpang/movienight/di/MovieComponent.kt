package com.eungpang.movienight.di

import com.eungpang.movienight.ui.MovieFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ApplicationModule::class, MovieModule::class, NetworkModule::class]
)
interface MovieComponent {

    fun inject(movieFragment: MovieFragment)

}