package com.eungpang.movienight.utils

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.eungpang.movienight.R
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun ImageView.loadingImg(imageUrl: String) {
    if (TextUtils.isEmpty(imageUrl)) {
        Picasso.get().load(R.mipmap.ic_launcher).into(this)
    } else {
        Picasso.get().load(imageUrl)
            .placeholder(R.drawable.img_default)
            //.resize(300,300)  // 300x300 픽셀
            .centerCrop()
            .fit()
            .into(this)
    }
}

// TODO: anko 로 대체 할 것.
fun RecyclerView.snackbar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration).show()
}
