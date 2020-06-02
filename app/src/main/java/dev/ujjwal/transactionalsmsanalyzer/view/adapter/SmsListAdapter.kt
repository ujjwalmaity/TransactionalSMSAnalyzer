package dev.ujjwal.transactionalsmsanalyzer.view.adapter

import android.content.Context
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
        private val tvSms = view.tv_sms

        fun bind(sms: SMSDetail, context: Context, position: Int) {
            if (sms.isCredited!!) {
                tvSms.text = "TAG: ${sms.tag}\nSENDER: ${sms.sender}\nCREDITED: ${sms.amount}\nON: ${Date(sms.date!!.toLong())}\n\n${sms.body}"
            } else {
                tvSms.text = "TAG: ${sms.tag}\nSENDER: ${sms.sender}\nDEBITED: ${sms.amount}\nON: ${Date(sms.date!!.toLong())}\n\n${sms.body}"
            }

            cardView.setOnClickListener {
                (context as MainActivity).changeSmsTag(sms._id!!)
            }
        }
    }
}