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
import kotlinx.android.synthetic.main.fragment_movie.*

class MovieFragment : Fragment() {
    private val recyclerView by lazy {
        recycler_view
    }

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
            val movieList = mutableListOf<Movie>()
            for (i in 1..10) {
                movieList.apply {
                    add(
                        Movie(
                            1234,
                            5.0f,
                            "Movie ${(i + 0)}",
                            "2020-$i-${i + 0}",
                            "https://picsum.photos/480/640?image=$i", // (3) 외부 인터넷 이미지 리소스
                            "Movie${i + 0}"
                        )
                    )

                    add(
                        Movie(
                            1234,
                            5.0f,
                            "Movie ${i + 1}",
                            "2019-$i-${i + 1}",
                            "https://picsum.photos/480/640?image=$i", // (3) 외부 인터넷 이미지 리소스
                            "Movie${i + 1}"
                        )
                    )

                    add(
                        Movie(
                            1234,
                            5.0f,
                            "Movie ${i + 2}",
                            "2018-$i-${i + 2}",
                            "https://picsum.photos/480/640?image=$i", // (3) 외부 인터넷 이미지 리소스
                            "Movie${i + 2}"
                        )
                    )
                }
            }

            (recyclerView.adapter as MovieAdapter).addMovieList(movieList)
        } else {

        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = MovieFragment()
    }
}
