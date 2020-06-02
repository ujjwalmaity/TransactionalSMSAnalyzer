package dev.ujjwal.transactionalsmsanalyzer.view.activity

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import dev.ujjwal.transactionalsmsanalyzer.R
import dev.ujjwal.transactionalsmsanalyzer.model.SMSDetail
import dev.ujjwal.transactionalsmsanalyzer.model.smsList
import dev.ujjwal.transactionalsmsanalyzer.util.*
import dev.ujjwal.transactionalsmsanalyzer.view.adapter.SmsListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.regex.Pattern


class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
        private const val MY_PERMISSIONS_REQUEST_READ_SMS = 1
    }

    private val smsListAdapter = SmsListAdapter(this, arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermission()

        recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = smsListAdapter
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED) {
            refreshSmsInbox()
        }

        btn_total.setOnClickListener {
            gotoChatActivity("total")
        }
        btn_monthly.setOnClickListener {
            gotoChatActivity("monthly")
        }
        btn_daily.setOnClickListener {
            gotoChatActivity("daily")
        }
        btn_tag.setOnClickListener {
            gotoChatActivity("tag")
        }
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_SMS), MY_PERMISSIONS_REQUEST_READ_SMS)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_SMS -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Log.i(TAG, "Permission was granted for Read SMS")
                    refreshSmsInbox()
                } else {
                    Log.i(TAG, "Permission was denied for Read SMS")
                    Toast.makeText(applicationContext, "Permission was denied for Read SMS", Toast.LENGTH_LONG).show()
                }
            }

            else -> {
                // Ignore all other requests.
            }
        }
    }

    private fun refreshSmsInbox() {
        val contentResolver = contentResolver
        val smsInboxCursor: Cursor? = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null)
        val indexId: Int = smsInboxCursor!!.getColumnIndex("_id")
        val indexDate: Int = smsInboxCursor.getColumnIndex("date")
        val indexBody: Int = smsInboxCursor.getColumnIndex("body")
        val indexAddress: Int = smsInboxCursor.getColumnIndex("address")
        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return
        smsList.clear()
        do {
            val id = smsInboxCursor.getString(indexId).trimIndent()
            val date = smsInboxCursor.getString(indexDate).trimIndent()
            val sender = smsInboxCursor.getString(indexAddress).trimIndent()
            val body = smsInboxCursor.getString(indexBody).trimIndent()

            val isValidTransaction = body.contains("credited") || body.contains("received") || body.contains("debited") || body.contains("withdrawn")
            val regEx = Pattern.compile("(?i)(?:(?:RS|INR|MRP)\\.?\\s?)(\\d+(:?\\,\\d+)?(\\,\\d+)?(\\.\\d{1,2})?)")
            val m = regEx.matcher(body)
            if (isValidTransaction && m.find()) {
                val smsDetail = SMSDetail()
                smsDetail._id = id
                smsDetail.date = date
                smsDetail.sender = sender
                smsDetail.body = body
                smsDetail.isCredited = getCreditStatus(body)
                smsDetail.amount = getAmount(m.group(0)!!)
                smsList.add(smsDetail)
            }
        } while (smsInboxCursor.moveToNext())
        smsListAdapter.updateSms(smsList)
    }
}