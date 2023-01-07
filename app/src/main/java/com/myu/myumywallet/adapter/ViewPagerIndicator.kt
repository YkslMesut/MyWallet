package com.myu.myumywallet.adapter

import android.content.Context
import android.media.Image
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.myu.myumywallet.R

class ViewPagerIndicator(
    private val imageViewPagerAdapter: ImageViewPagerAdapter,
    private val mContext : Context,
    private val sliderDotsPanel : LinearLayout) {

   private val dotsCount : Int
       get()  = imageViewPagerAdapter.itemCount

    lateinit var dots : ArrayList<ImageView>

    fun loadDots(currentPageIndex : Int) : LinearLayout{
        dots = arrayListOf<ImageView>()

        for (dot in 0  until dotsCount) {
            dots.add(dot, ImageView(mContext))
            dots[dot].setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_default_dots))
            sliderDotsPanel.addView(dots[dot])
        }
        dots[currentPageIndex].setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_active_dots))
        return sliderDotsPanel
    }

    fun slideDots(position : Int) : LinearLayout {
        var slidedDots = dots

        for (dot in 0 until dotsCount) {
            slidedDots.get(dot).setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_default_dots))
        }
        slidedDots.get(position).setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_active_dots))

        return sliderDotsPanel
    }


}