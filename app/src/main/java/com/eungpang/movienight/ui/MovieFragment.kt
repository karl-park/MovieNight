package com.eungpang.movienight.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.eungpang.movienight.BuildConfig
import com.eungpang.movienight.MyApplication
import com.eungpang.movienight.R
import com.eungpang.movienight.entity.MovieList
import com.eungpang.movienight.ui.adapter.MovieAdapter
import com.eungpang.movienight.ui.adapter.ViewSelectedListener
import com.eungpang.movienight.utils.androidLazy
import com.eungpang.movienight.utils.inflate
import com.eungpang.movienight.utils.snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieFragment : RxBaseFragment() {
    private val recyclerView by lazy {
        recycler_view
    }

    @Inject lateinit var movieManager : MovieManager

    private var movieList : MovieList? = null

    private val movieAdapter by androidLazy {
        MovieAdapter(movieSelectedListener)
    }

    private val movieSelectedListener = object: ViewSelectedListener {
        override fun onItemSelected(url: String?) {
            if (url.isNullOrEmpty()) {
                recyclerView.snackbar("No URL assigned to this results")
            } else {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.movieComponent.inject(this)
    }

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
                requestMovieWithRxJava()
            }, linearLayoutManager))

            if (adapter != null) {
                return@apply
            }

            adapter = movieAdapter
        }

        // First launch
        if (savedInstanceState == null) {
            requestMovieWithRxJava()
            return
        }

        // If there is a saved data.
        if (savedInstanceState.containsKey(INTENT_KEY_MOVIE_DATA)) {
            movieList = savedInstanceState.get(INTENT_KEY_MOVIE_DATA) as MovieList
            movieAdapter.clearAndAddMovieList(movieList!!.results)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val movies = movieAdapter.getMovieList()
        if (movies.isNotEmpty() && movieList != null) {
            outState.putParcelable(INTENT_KEY_MOVIE_DATA, movieList?.copy(results = movies))
        }
    }

    private fun requestMovieWithRxJava() {
        val subscription = movieManager.getMovieListWithRxJava((movieList?.page).toString())
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

    private fun requestMovieWithCoroutine() {
        job = GlobalScope.launch(Dispatchers.Main) {
            try {
                val param = mapOf(
                    "page" to movieList?.page.toString(),
                    "api_key" to BuildConfig.API_KEY,
                    "sort_by" to "popularity.desc",
                    "language" to "ko"
                )
                val retrievedMovie = movieManager.getMovieListWithCoroutine(param)
                retrievedMovie.page = retrievedMovie.page?.plus(1)

                movieList = retrievedMovie
                movieAdapter.addMovieList(retrievedMovie.results)
            } catch (e: Exception) {
                recyclerView.snackbar(e.message ?: "")
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MovieFragment()

        const val INTENT_KEY_MOVIE_DATA = "INTENT_KEY_MOVIE_DATA"
    }
}
