package dev.ujjwal.transactionalsmsanalyzer.util

import android.content.Context
import android.content.Intent
import dev.ujjwal.transactionalsmsanalyzer.view.activity.ChartActivity
import java.util.*

fun getCreditStatus(body: String): Boolean {
    if (body.contains("credited") || body.contains("received")) {
        return true
    } else if (body.contains("debited") || body.contains("withdrawn")) {
        return false
    }
    return false
}

fun isThisMonth(timeStamp: String): Boolean {
    val previousDay = Date(timeStamp.toLong()).time
    val today = Date(System.currentTimeMillis()).time

    val diff: Long = today - previousDay
    val seconds = diff / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24

    return days <= 30
}

fun isToday(timeStamp: String): Boolean {
    val previousDay = Date(timeStamp.toLong()).time
    val today = Date(System.currentTimeMillis()).time

    val diff: Long = today - previousDay
    val seconds = diff / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24

    return days <= 0
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

fun Context.gotoChatActivity(msg: String) {
    val i = Intent(this, ChartActivity::class.java)
    i.putExtra("data", msg)
    startActivity(i)
}