package dev.ujjwal.transactionalsmsanalyzer.view.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.ujjwal.transactionalsmsanalyzer.R
import dev.ujjwal.transactionalsmsanalyzer.model.SMSDetail
import dev.ujjwal.transactionalsmsanalyzer.view.activity.MainActivity
import kotlinx.android.synthetic.main.item_sms.view.*
import java.util.*

class SmsListAdapter(private val context: Context, private var smses: ArrayList<SMSDetail>) : RecyclerView.Adapter<SmsListAdapter.SmsListViewHolder>() {

    fun updateSms(sms: List<SMSDetail>) {
        this.smses.clear()
        this.smses.addAll(sms)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmsListViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_sms, parent, false)
        return SmsListViewHolder(v)
    }

    override fun onBindViewHolder(holder: SmsListViewHolder, position: Int) {
        holder.bind(smses[position], context, position)
    }

    override fun getItemCount() = smses.size

    class SmsListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val cardView = view.sms_item
        private val tvSmsTag = view.tv_sms_tag
        private val tvSmsSender = view.tv_sms_sender
        private val tvSmsBody = view.tv_sms_body
        private val tvSmsAmount = view.tv_sms_amount
        private val tvSmsTime = view.tv_sms_time

        fun bind(sms: SMSDetail, context: Context, position: Int) {
            tvSmsTag.text = sms.tag?.toUpperCase()
            tvSmsSender.text = sms.sender
            tvSmsBody.text = "${sms.body}"
            tvSmsTime.text = "\n${Date(sms.date!!.toLong())}"
            if (sms.isCredited!!) {
                tvSmsAmount.text = "+${sms.amount}"
            } else {
                tvSmsAmount.text = "-${sms.amount}"
                tvSmsAmount.setTextColor(Color.RED)
            }

            cardView.setOnClickListener {
                (context as MainActivity).changeSmsTag(sms._id!!)
            }
        }
    }
}