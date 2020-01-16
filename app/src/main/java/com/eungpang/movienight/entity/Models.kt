package com.eungpang.movienight.entity

import android.os.Parcelable
import com.eungpang.movienight.ui.adapter.AdapterType
import com.eungpang.movienight.ui.adapter.ViewType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val vote_count: Int,
    val vote_average: Float,
    val title: String,
    val release_date: String,
    val poster_path: String,
    val overview: String?
) : Parcelable, ViewType {

    override fun getViewType(): Int = AdapterType.MOVIE
}

@Parcelize
data class MovieList(
    var page: Int?,
    val results: List<Movie>) : Parcelable {

}