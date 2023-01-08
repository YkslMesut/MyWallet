package com.myu.myumywallet.data.repository

import com.myu.myumywallet.data.model.WalletData
import com.myu.myumywallet.data.remote.WalletService
import com.myu.myumywallet.utils.Status
import java.util.Calendar

class WalletRepository(
    private val api : WalletService
) {
    suspend fun getWalletsData () : com.myu.myumywallet.utils.Result<WalletData> {
        return try {
            val response = api.getWalletsData()

            if (response.isSuccessful){
                var responseData = response.body()
                responseData?.let { result ->
                    for (item in 0 until result.size) {
                        val currentTime = Calendar.getInstance().time
                        responseData[item].fetchTime = currentTime
                    }
                }
                com.myu.myumywallet.utils.Result(Status.SUCCESS,responseData,null)
            } else {
                com.myu.myumywallet.utils.Result(Status.ERROR,null,null)
            }
        } catch (e : Exception) {
            com.myu.myumywallet.utils.Result(Status.ERROR,null,e.message)
        }
    }
}