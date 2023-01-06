package com.myu.myumywallet.data.remote

import com.myu.myumywallet.data.model.WalletData
import retrofit2.Response
import retrofit2.http.GET

interface WalletService {

    @GET ("wallets")
    suspend fun getWalletsData() : Response<WalletData>
}