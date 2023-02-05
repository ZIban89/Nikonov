package com.example.tinkoffmovies.common

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.load(url: String) {
    Glide
        .with(this.context)
        .load(url)
        .fitCenter()
        .into(this)
}