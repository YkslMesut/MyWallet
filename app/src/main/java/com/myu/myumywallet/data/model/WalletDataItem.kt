package com.myu.myumywallet.data.model

data class WalletDataItem(
    val balance: Balance,
    val cvv: String,
    val image: String,
    val number: String,
    val pendingBalance: PendingBalance
)