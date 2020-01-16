package com.eungpang.movienight.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eungpang.movienight.R
import com.eungpang.movienight.entity.Movie
import com.eungpang.movienight.utils.inflate
import com.eungpang.movienight.utils.loadingImg
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieItemAdapter(val listener: ViewSelectedListener) : ItemAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return MovieItemViewHoler(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        (holder as MovieItemViewHoler).bind(item as Movie)
    }

    inner class MovieItemViewHoler(parent: ViewGroup)
        : RecyclerView.ViewHolder(parent.inflate(R.layout.item_movie)) {

        private val imgPoster = itemView.img_poster
        private val overview = itemView.tv_overview
        private val releaseDate = itemView.tv_release_date
        private val voteCount = itemView.tv_vote_count
        private val tvTitle = itemView.tv_title
        private val tvAverage = itemView.rate_vote_avg
        private val btnReserve = itemView.btn_reserve

        fun bind(item: Movie) {
            imgPoster.loadingImg("https://image.tmdb.org/t/p/w500/${item.poster_path}")
            overview.text = item.overview
            releaseDate.text = item.release_date
            voteCount.text = "${item.vote_count} likes!"
            tvTitle.text = item.title
            tvAverage.rating = item.vote_average / 2

            itemView.setOnClickListener {
                listener.onItemSelected("https://image.tmdb.org/t/p/w500/${item.poster_path}")
            }
        }
    }

}