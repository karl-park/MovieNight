package com.eungpang.movienight.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val vote_count: Int,
    val vote_average: Float,
    val title: String,
    val release_date: String,
    val poster_path: String,
    val overview: String?
) : Parcelable {

}

@Parcelize
data class MovieList(
    var page: Int?,
    val results: List<Movie>) : Parcelable {

}