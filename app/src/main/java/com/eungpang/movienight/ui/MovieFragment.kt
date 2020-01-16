package com.eungpang.movienight.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.eungpang.movienight.R
import com.eungpang.movienight.entity.Movie
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            = container?.inflate(R.layout.fragment_movie)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerView.apply {
            setHasFixedSize(true)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                recyclerView.layoutManager = LinearLayoutManager(context)
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
        val subscription = movieManager.getMovieList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe (
                { retrievedMovie ->
                    (recyclerView.adapter as MovieAdapter).addMovieList(retrievedMovie)
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
