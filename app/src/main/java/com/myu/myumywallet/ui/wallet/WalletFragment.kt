package com.myu.myumywallet.ui.wallet

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.myu.myumywallet.R
import com.myu.myumywallet.adapter.ImageViewPagerAdapter
import com.myu.myumywallet.data.model.WalletDataItem
import com.myu.myumywallet.databinding.FragmentWalletBinding
import com.myu.myumywallet.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalletFragment : Fragment() {
    private var _binding : FragmentWalletBinding? = null
    private val binding get() = _binding!!
    private lateinit var mContext : Context
    private val viewModel : WalletViewModel by viewModels()
    private lateinit var imageViewPagerAdapter: ImageViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWalletBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeWalletData()
        viewModel.getWalletData()
    }

    private fun observeWalletData() {
        viewModel.walletResponse.observe(viewLifecycleOwner, Observer { result ->
            when(result.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {
                    result.data?.let { data -> setUpViewPager(data) }
                }
                else -> {

                }
            }
        })
    }

    private fun setUpViewPager(dataList : List<WalletDataItem>) {
        imageViewPagerAdapter = ImageViewPagerAdapter(dataList)

        binding.viewPager.adapter = imageViewPagerAdapter

        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val currentPageIndex = 0
        binding.viewPager.currentItem = currentPageIndex
        //changeIndicator(currentPageIndex)

        binding.viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    //changeIndicator(position)
                }
            }
        )
    }

}