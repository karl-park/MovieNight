package com.eungpang.movienight.di

import com.eungpang.movienight.api.MovieApi
import com.eungpang.movienight.api.MovieApiImpl
import com.eungpang.movienight.api.MovieService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class MovieModule {

    @Provides
    @Singleton
    fun provideMovieService(retrofit: Retrofit): MovieService =
        retrofit.create(MovieService::class.java)

    @Provides
    @Singleton
    fun provideMovieApi(movieService: MovieService): MovieApi =
        MovieApiImpl(movieService)

}