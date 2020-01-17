package com.eungpang.movienight.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.eungpang.movienight.R
import com.eungpang.movienight.entity.MovieList
import com.eungpang.movienight.ui.adapter.MovieAdapter
import com.eungpang.movienight.ui.adapter.ViewSelectedListener
import com.eungpang.movienight.utils.inflate
import com.eungpang.movienight.utils.snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_movie.*

class MovieFragment : RxBaseFragment() {
    private val recyclerView by lazy {
        recycler_view
    }

    private val movieManager by lazy { MovieManager() }
    private var movieList : MovieList? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            = container?.inflate(R.layout.fragment_movie)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerView.apply {
            setHasFixedSize(true)
            val linearLayoutManager = LinearLayoutManager(context)
            layoutManager = linearLayoutManager

            clearOnScrollListeners()
            addOnScrollListener(InfiniteScrollListener({
                requestMovie()
            }, linearLayoutManager))

            if (adapter != null) {
                return@apply
            }

            adapter = MovieAdapter(object: ViewSelectedListener {
                override fun onItemSelected(url: String?) {
                    if (url.isNullOrEmpty()) {
                        recyclerView.snackbar("No URL assigned to this results")
                    } else {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(url)
                        startActivity(intent)
                    }
                }
            })
        }

        if (savedInstanceState == null) {
            requestMovie()
        } else {

        }
    }

    private fun requestMovie() {
        val subscription = movieManager.getMovieList((movieList?.page).toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe (
                { retrievedMovie ->
                    movieList = retrievedMovie
                    (recyclerView.adapter as MovieAdapter).addMovieList(retrievedMovie.results)
                },
                { e ->
                    recyclerView.snackbar(e.message ?: "")
                }
            )

        subscriptions.add(subscription)
    }

    companion object {
        @JvmStatic
        fun newInstance() = MovieFragment()
    }
}
