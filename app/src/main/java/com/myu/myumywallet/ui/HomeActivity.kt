package com.myu.myumywallet.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.myu.myumywallet.R
import com.myu.myumywallet.adapter.ViewPagerAdapter
import com.myu.myumywallet.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val TAG = "HomeActivity"

    lateinit var binding : ActivityHomeBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.homeFragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        binding.btnWallet.setOnClickListener {
            navController.navigateUp()
            navController.navigate(R.id.walletFragment)
        }
        binding.btnSalePoint.setOnClickListener {
            navController.navigateUp()
            navController.navigate(R.id.salePointFragment)
        }
    }


}