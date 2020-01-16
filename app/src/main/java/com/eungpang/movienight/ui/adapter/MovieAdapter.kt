package com.eungpang.movienight.ui.adapter

import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.RecyclerView
import com.eungpang.movienight.entity.Movie

class MovieAdapter(listener: ViewSelectedListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: ArrayList<ViewType>

    private var delegateAdapters = SparseArrayCompat<ItemAdapter>()

    private val loadingItem = object : ViewType {
        override fun getViewType() = AdapterType.LOADING
    }

    init {
        delegateAdapters.put(AdapterType.LOADING, LoadingItemAdapter())
        delegateAdapters.put(AdapterType.MOVIE, MovieItemAdapter(listener))
        items = ArrayList()
        items.add(loadingItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        delegateAdapters[viewType]!!.onCreateViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        delegateAdapters[getItemViewType(position)]!!.onBindViewHolder(holder, items[position])

    override fun getItemViewType(position: Int) = items[position].getViewType()

    override fun getItemCount(): Int = items.size

    fun addMovieList(movieList: List<Movie>) {
        val initPosition = items.size - 1
        items.removeAt(initPosition)
        notifyItemRemoved(initPosition)

        items.addAll(movieList)
        items.add(loadingItem)
        notifyItemRangeChanged(initPosition, items.size + 1)
    }

    fun clearAndAddMovieList(movieList: List<Movie>) {
        items.clear()
        notifyItemRangeRemoved(0, getLastPosition())

        items.addAll(movieList)
        items.add(loadingItem)
        notifyItemRangeInserted(0, items.size)
    }

    fun getMovieList(): List<Movie> = items
        .filter { it.getViewType() == AdapterType.MOVIE }
        .map { it as Movie }

    private fun getLastPosition() = if (items.lastIndex == -1) 0 else items.lastIndex
}
