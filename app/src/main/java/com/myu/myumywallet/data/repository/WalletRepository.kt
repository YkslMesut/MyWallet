package com.myu.myumywallet.data.repository

import com.myu.myumywallet.data.model.WalletData
import com.myu.myumywallet.data.remote.WalletService
import com.myu.myumywallet.utils.Status

class WalletRepository(
    private val api : WalletService
) {
    suspend fun getWalletsData () : com.myu.myumywallet.utils.Result<WalletData> {
        return try {
            val response = api.getWalletsData()
            if (response.isSuccessful){
                com.myu.myumywallet.utils.Result(Status.SUCCESS,response.body(),null)
            } else {
                com.myu.myumywallet.utils.Result(Status.ERROR,null,null)
            }
        } catch (e : Exception) {
            com.myu.myumywallet.utils.Result(Status.ERROR,null,e.message)
        }
    }
}