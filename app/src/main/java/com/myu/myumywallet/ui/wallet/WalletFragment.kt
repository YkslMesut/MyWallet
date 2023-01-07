package com.myu.myumywallet.ui.wallet

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.myu.myumywallet.adapter.ImageViewPagerAdapter
import com.myu.myumywallet.adapter.ViewPagerIndicator
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
    private lateinit var viewPagerIndicator: ViewPagerIndicator
    private lateinit var sliderDotsPanel: LinearLayout
    private var lastPosition = 0

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
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentWalletBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sliderDotsPanel = binding.SliderDots
        observeWalletData()
        viewModel.getWalletData()

        binding.refresh.setOnClickListener {
            viewModel.getWalletData()
        }
    }

    private fun observeWalletData() {
        viewModel.walletResponse.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {
                    result.data?.let { data ->
                        loadData(data)
                    }
                }
                else -> {

                }
            }
        }
    }

    private fun loadData(dataList : List<WalletDataItem>) {

        imageViewPagerAdapter = ImageViewPagerAdapter(dataList)
        binding.viewPager.adapter = imageViewPagerAdapter

        viewPagerIndicator = ViewPagerIndicator(imageViewPagerAdapter,mContext,sliderDotsPanel)
        sliderDotsPanel = viewPagerIndicator.loadDots(lastPosition)

        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.viewPager.currentItem = lastPosition
        binding.cardData = dataList[lastPosition]

        binding.viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    lastPosition = position
                    binding.cardData = dataList[position]
                    sliderDotsPanel = viewPagerIndicator.slideDots(lastPosition)
                }
            }
        )
    }
}