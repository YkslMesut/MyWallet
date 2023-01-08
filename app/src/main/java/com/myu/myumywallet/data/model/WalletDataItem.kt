package com.myu.myumywallet.data.model

import java.util.Date

data class WalletDataItem(
    val balance: Balance?,
    val cvv: String?,
    val image: String?,
    val number: String?,
    val pendingBalance: PendingBalance?,
    var fetchTime : Date?
)