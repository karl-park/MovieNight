package com.eungpang.movienight.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eungpang.movienight.R
import com.eungpang.movienight.utils.inflate
import kotlinx.android.synthetic.main.fragment_movie.*

class MovieFragment : Fragment() {
    private val recyclerView by lazy {
        recycler_view
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            = container?.inflate(R.layout.fragment_movie)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerView.setHasFixedSize(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            recyclerView.layoutManager = LinearLayoutManager(context)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MovieFragment()
    }
}
