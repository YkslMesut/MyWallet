package com.myu.myumywallet.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.myu.myumywallet.R
import com.squareup.picasso.Picasso

@BindingAdapter("loadImage")
fun loadImage(view: ImageView, url: String?) {

    url?.let {
        Picasso.get()
            .load(it)
            .fit()
            .placeholder(R.drawable.ic_baseline_refresh_24)
            .into(view)
    }
}

@BindingAdapter("loadCvv")
fun loadCvvNumber(view : TextView , cvvNumber: String?) {
    cvvNumber?.let {  cv ->
        view.text = cv.takeLast(4)
    }
}