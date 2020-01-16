package com.eungpang.movienight.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

object AdapterType {
    val MOVIE = 0
    val LOADING = 1
}

interface ViewSelectedListener {
    fun onItemSelected(url: String?)
}

interface ViewType {
    fun getViewType() : Int
}

interface ItemAdapter {
    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType)
}
