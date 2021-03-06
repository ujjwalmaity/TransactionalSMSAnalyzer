package dev.ujjwal.transactionalsmsanalyzer.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.enums.Align
import com.anychart.enums.LegendLayout
import dev.ujjwal.transactionalsmsanalyzer.R
import dev.ujjwal.transactionalsmsanalyzer.model.smsList
import dev.ujjwal.transactionalsmsanalyzer.util.isThisMonth
import dev.ujjwal.transactionalsmsanalyzer.util.isToday
import kotlinx.android.synthetic.main.activity_chart.*

class ChartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)

        val data = intent.getStringExtra("data")

        data?.let {
            when (it) {
                "total" -> setupPieChart("Total Report", calculateTotal())
                "monthly" -> setupPieChart("Monthly Report", calculateMonthly())
                "daily" -> setupPieChart("Daily Report", calculateDaily())
                "tag" -> setupPieChart("Tag Wise Expenses", calculateTag())
            }
        }
    }

    private fun setupPieChart(title: String, data: MutableList<DataEntry>) {
        val pie = AnyChart.pie()

        pie.data(data)

        pie.title(title)

        pie.labels().position("outside")

        pie.legend().title().enabled(true)
        pie.legend().title()
            .text("Finance analysis")
            .padding(0.0, 0.0, 10.0, 0.0)

        pie.legend()
            .position("center-bottom")
            .itemsLayout(LegendLayout.HORIZONTAL)
            .align(Align.CENTER)

        any_chart_view.setChart(pie)
    }

    private fun calculateTotal(): MutableList<DataEntry> {
        val data: MutableList<DataEntry> = ArrayList()
        var totalExpenses: Double = 0.0
        var totalIncome: Double = 0.0

        for (sms in smsList) {
            if (sms.isCredited!!)
                totalIncome += sms.amount!!.toDouble()
            else
                totalExpenses += sms.amount!!.toDouble()
        }
        data.add(ValueDataEntry("Expenses", totalExpenses))
        data.add(ValueDataEntry("Income", totalIncome))

        return data
    }

    private fun calculateMonthly(): MutableList<DataEntry> {
        val data: MutableList<DataEntry> = ArrayList()
        var totalExpenses: Double = 0.0
        var totalIncome: Double = 0.0

        for (sms in smsList) {
            if (isThisMonth(sms.date!!)) {
                if (sms.isCredited!!)
                    totalIncome += sms.amount!!.toDouble()
                else
                    totalExpenses += sms.amount!!.toDouble()
            }
        }
        data.add(ValueDataEntry("Expenses", totalExpenses))
        data.add(ValueDataEntry("Income", totalIncome))

        return data
    }

    private fun calculateDaily(): MutableList<DataEntry> {
        val data: MutableList<DataEntry> = ArrayList()
        var totalExpenses: Double = 0.0
        var totalIncome: Double = 0.0

        for (sms in smsList) {
            if (isToday(sms.date!!)) {
                if (sms.isCredited!!)
                    totalIncome += sms.amount!!.toDouble()
                else
                    totalExpenses += sms.amount!!.toDouble()
            }
        }
        data.add(ValueDataEntry("Expenses", totalExpenses))
        data.add(ValueDataEntry("Income", totalIncome))

        return data
    }

    private fun calculateTag(): MutableList<DataEntry> {
        val data: MutableList<DataEntry> = ArrayList()
        var totalExpenses: Double = 0.0

        val allTags = ArrayList<String>()
        for (sms in smsList) {
            allTags.add(sms.tag!!)
        }
        val uniqueTags = allTags.distinct()

        for (tag in uniqueTags) {
            if (tag == "") continue
            for (sms in smsList) {
                if (sms.tag != tag) continue
                if (!sms.isCredited!!)
                    totalExpenses += sms.amount!!.toDouble()
            }
            if (totalExpenses <= 0) continue
            data.add(ValueDataEntry(tag.toUpperCase(), totalExpenses))
            totalExpenses = 0.0
        }

        return data
    }
}