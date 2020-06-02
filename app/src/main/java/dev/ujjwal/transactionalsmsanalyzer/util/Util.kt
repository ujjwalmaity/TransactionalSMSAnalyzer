package dev.ujjwal.transactionalsmsanalyzer.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun getCreditStatus(body: String): Boolean {
    if (body.contains("credited") || body.contains("received")) {
        return true
    } else if (body.contains("debited") || body.contains("withdrawn")) {
        return false
    }
    return false
}

fun getBeautifulDate(timeStamp: String): String {
    var str = ""
    val sdf = SimpleDateFormat("ddMMyyyyHHmmss")
    try {
        val date = sdf.parse(timeStamp)
        str = "$date"
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return str
}

fun getCurrentTimestamp(): String? {
    val sdf = SimpleDateFormat("ddMMyyyyHHmmss")
    val resultDate = Date(System.currentTimeMillis())
    return sdf.format(resultDate)
}

fun getAmount(str: String): String {
    var amount: String = str
    amount = amount.replace("Re.", "")
    amount = amount.replace("Re", "")
    amount = amount.replace("re", "")
    amount = amount.replace("Rs.", "")
    amount = amount.replace("Rs", "")
    amount = amount.replace("rs", "")
    amount = amount.replace("INR", "")
    amount = amount.replace("inr", "")
    amount = amount.replace(" ", "")
    amount = amount.replace(",", "")
    return amount
}