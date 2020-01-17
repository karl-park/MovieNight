package com.eungpang.movienight.di

import android.app.Application
import android.content.Context
import com.eungpang.movienight.MyApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(val application: MyApplication) {

    @Provides
    @Singleton
    fun provideContext() : Context = application

    @Provides
    @Singleton
    fun provideApplication() : Application = application

}