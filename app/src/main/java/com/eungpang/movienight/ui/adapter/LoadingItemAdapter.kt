package com.eungpang.movienight.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eungpang.movienight.R
import com.eungpang.movienight.utils.inflate

class LoadingItemAdapter : ItemAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        LoadingItemViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        // nop
    }

    inner class LoadingItemViewHolder(parent: ViewGroup)
        : RecyclerView.ViewHolder(parent.inflate(R.layout.item_loading))
}