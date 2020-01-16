package com.eungpang.movienight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.eungpang.movienight.ui.MovieFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolBar)

        if (savedInstanceState == null) {
            changeFragment(MovieFragment.newInstance())
        }
    }

    fun changeFragment(fragment: Fragment, cleanStack: Boolean = false) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.base_content, fragment)
            addToBackStack(null)
            commit()
        }
    }
}
