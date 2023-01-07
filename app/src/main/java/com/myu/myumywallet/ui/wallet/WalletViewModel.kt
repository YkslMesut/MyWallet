package com.myu.myumywallet.ui.wallet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myu.myumywallet.data.model.WalletData
import com.myu.myumywallet.data.repository.WalletRepository
import com.myu.myumywallet.utils.Result
import com.myu.myumywallet.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val repository: WalletRepository
) : ViewModel() {
    private val _walletResponse : MutableLiveData<com.myu.myumywallet.utils.Result<WalletData>> = MutableLiveData()
    val walletResponse : LiveData<com.myu.myumywallet.utils.Result<WalletData>>
    get() = _walletResponse

    fun getWalletData() = viewModelScope.launch {
        _walletResponse.value = Result(Status.LOADING,null,null)
        _walletResponse.value = repository.getWalletsData()
    }
}