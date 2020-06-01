package dev.ujjwal.transactionalsmsanalyzer.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.ujjwal.transactionalsmsanalyzer.R
import dev.ujjwal.transactionalsmsanalyzer.model.SMS
import kotlinx.android.synthetic.main.item_sms.view.*
import java.util.ArrayList

class SmsListAdapter(private val context: Context, private var sms: ArrayList<SMS>) : RecyclerView.Adapter<SmsListAdapter.SmsListViewHolder>() {

    fun updateSms(sms: List<SMS>) {
        this.sms.clear()
        this.sms.addAll(sms)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmsListViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_sms, parent, false)
        return SmsListViewHolder(v)
    }

    override fun onBindViewHolder(holder: SmsListViewHolder, position: Int) {
        holder.bind(sms[position], context, position)
    }

    override fun getItemCount() = sms.size

    class SmsListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvSms = view.tv_sms

        fun bind(sms: SMS, context: Context, position: Int) {
            tvSms.text = sms.smsBody
        }
    }
}