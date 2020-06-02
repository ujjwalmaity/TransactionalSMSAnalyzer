package dev.ujjwal.transactionalsmsanalyzer.model

data class SMSDetail(
    var _id: String? = null,
    var date: String? = null,
    var sender: String? = null,
    var body: String? = null,

    var isCredited: Boolean? = null,
    var beautifulDate: String? = null,
    var amount: String? = null,
    var tag: String? = null
)