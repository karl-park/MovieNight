package com.eungpang.movienight.ui

import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Job

open class RxBaseFragment : Fragment() {
    protected var subscriptions = CompositeDisposable()
    protected var job : Job? = null

    override fun onResume() {
        super.onResume()
        subscriptions = CompositeDisposable()
        job = null

    }

    override fun onPause() {
        super.onPause()
        subscriptions.clear()

        job?.cancel()
        job = null
    }
}