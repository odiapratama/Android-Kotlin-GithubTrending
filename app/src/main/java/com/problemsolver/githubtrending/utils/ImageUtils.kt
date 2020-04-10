package com.problemsolver.githubtrending.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("loadImage")
fun ImageView.loadImage(url: String?) {
    this.loadWithCircleCrop(url ?: "")
}