package dev.ujjwal.transactionalsmsanalyzer.model

val smsList = ArrayList<SMSDetail>()

data class SMSDetail(
    var _id: String? = null,
    var date: String? = null,
    var sender: String? = null,
    var body: String? = null,

    var isCredited: Boolean? = null,
    var amount: String? = null,
    var tag: String? = null
)