package com.problemsolver.githubtrending.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadWithCircleCrop(url: String) {
    Glide.with(this)
        .load(url.trim())
        .apply(RequestOptions.circleCropTransform())
        .into(this)
}