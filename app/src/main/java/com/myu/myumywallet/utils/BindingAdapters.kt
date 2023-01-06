package com.myu.myumywallet.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.myu.myumywallet.R
import com.squareup.picasso.Picasso

@BindingAdapter("load")
fun loadImage(view: ImageView, url: String?) {

    url?.let {
        Picasso.get()
            .load(it)
            .fit()
            .placeholder(R.drawable.ic_baseline_refresh_24)
            .into(view)
    }
}