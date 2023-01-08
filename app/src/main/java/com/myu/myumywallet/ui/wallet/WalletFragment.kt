package com.myu.myumywallet.ui.wallet

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.myu.myumywallet.adapter.ImageViewPagerAdapter
import com.myu.myumywallet.adapter.ViewPagerIndicator
import com.myu.myumywallet.data.model.WalletDataItem
import com.myu.myumywallet.databinding.FragmentWalletBinding
import com.myu.myumywallet.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit


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
    private var myWalletList = arrayListOf<WalletDataItem>()

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

    private fun loadingUi(){
        binding.progressBar.visibility = VISIBLE
        binding.SliderDots.visibility = GONE
        binding.viewPager.visibility = GONE
    }

    private fun showUi(){
        binding.progressBar.visibility = GONE
        binding.SliderDots.visibility = VISIBLE
        binding.viewPager.visibility = VISIBLE
    }

    private fun observeWalletData() {
        viewModel.walletResponse.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.LOADING -> {
                loadingUi()
                }
                Status.SUCCESS -> {
                    showUi()
                    result.data?.let { data ->
                        myWalletList = data
                        if (!refreshTimer.isActive) {
                            refreshTimer.start()
                        }
                        loadData()
                    }
                }
                else -> {
                    showUi()
                }
            }
        }

    }
      private val refreshTimer =lifecycleScope.launch {

        repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.refreshTimer.catch {
                    exception -> Toast.makeText(mContext,exception.message,Toast.LENGTH_LONG).show()
            }.cancellable().collect{
                val different =
                    Calendar.getInstance().time.time - (myWalletList[lastPosition].fetchTime?.time ?: 0)
                val diffInSec: Long = TimeUnit.MILLISECONDS.toSeconds(different)

                binding.lastRefresh = diffInSec.toString()
            }
        }

    }

    private fun stopTrackRefresh () {
        refreshTimer.cancel()
    }

    private fun loadData() {

        imageViewPagerAdapter = ImageViewPagerAdapter(myWalletList)
        binding.viewPager.adapter = imageViewPagerAdapter

        viewPagerIndicator = ViewPagerIndicator(
            imageViewPagerAdapter,
            mContext,
            sliderDotsPanel)

        sliderDotsPanel = viewPagerIndicator.loadDots(lastPosition)

        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.viewPager.currentItem = lastPosition
        binding.cardData = myWalletList[lastPosition]

        binding.viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    lastPosition = position
                    binding.cardData = myWalletList[position]
                    sliderDotsPanel = viewPagerIndicator.slideDots(lastPosition)
                }
            }
        )
    }
}