package com.myu.myumywallet.utils

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.myu.myumywallet.R
import com.squareup.picasso.Picasso
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

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

@BindingAdapter("loadLastRefreshTime")
fun loadLastRefreshTime(view : TextView , lastRefreshTime: String?) {
    lastRefreshTime?.let {  refreshTime ->
        view.text = "$refreshTime Second Ago"
    }
}

@BindingAdapter("loadCurrency")
fun loadCurrency(view : TextView , currency: String?) {
    view.text = calculateCurrency(currency)
}

fun calculateCurrency(currency: String?) : String {
    val dots = "."
   val  result  =    currency?.let { currencyResponse ->
        val penny = dots + currencyResponse.takeLast(2)
        val money = currencyResponse.dropLast(2)

        val dec = DecimalFormat("###,###")
        val formattedNumber = dec.format(money.toDouble())


         formattedNumber + penny
    } ?:""
    return result
}

