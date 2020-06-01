package dev.ujjwal.transactionalsmsanalyzer.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import dev.ujjwal.transactionalsmsanalyzer.R
import dev.ujjwal.transactionalsmsanalyzer.model.SMS
import dev.ujjwal.transactionalsmsanalyzer.view.adapter.SmsListAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val smsListAdapter = SmsListAdapter(this, arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = smsListAdapter
        }

        val userList = ArrayList<SMS>()
        userList.add(SMS('a'.toString()))
        userList.add(SMS('b'.toString()))
        userList.add(SMS('c'.toString()))
        userList.add(SMS('d'.toString()))
        smsListAdapter.updateSms(userList)
    }
}