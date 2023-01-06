package com.myu.myumywallet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myu.myumywallet.R
import com.myu.myumywallet.data.model.WalletDataItem
import com.myu.myumywallet.databinding.ItemWalletCardBinding
import com.squareup.picasso.Picasso

class ImageViewPagerAdapter(private val walletResultList: List<WalletDataItem>) :
    RecyclerView.Adapter<ImageViewPagerAdapter.ViewPagerViewHolder>() {

    inner class ViewPagerViewHolder(val binding: ItemWalletCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(wallet: WalletDataItem) {
            binding.cardNumber.text = wallet.number.takeLast(4)
            binding.cvvNumber.text = wallet.cvv

            Picasso.get()
                .load(wallet.image)
                .fit()
                .placeholder(R.drawable.ic_baseline_refresh_24)
                .into(binding.cardImage)
        }
    }

    override fun getItemCount(): Int = walletResultList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {

        val binding = ItemWalletCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewPagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.setData(walletResultList[position])
    }

}