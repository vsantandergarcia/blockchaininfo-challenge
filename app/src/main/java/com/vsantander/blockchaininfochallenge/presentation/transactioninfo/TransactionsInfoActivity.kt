package com.vsantander.blockchaininfochallenge.presentation.transactioninfo

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.vsantander.blockchaininfochallenge.R
import com.vsantander.blockchaininfochallenge.extension.observe
import com.vsantander.blockchaininfochallenge.presentation.base.activity.BaseActivity
import javax.inject.Inject
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.components.XAxis
import android.support.v4.content.ContextCompat
import androidx.core.view.isVisible
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.vsantander.blockchaininfochallenge.domain.model.TransactionPerSecond
import com.vsantander.blockchaininfochallenge.presentation.model.Status
import kotlinx.android.synthetic.main.activity_transaction_info.*


@BaseActivity.Animation(BaseActivity.FADE)
class TransactionsInfoActivity : BaseActivity() {

    companion object {
        private const val ANIMATION_DURATION_GRAPHIC = 2500
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: TransactionsInfoViewModel

    @Inject
    lateinit var formatter: TimeAxisValueFormatter

    /* Activity methods */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_info)
        setUpViews()
        setUpViewModel()
    }

    /* setUp methods */

    private fun setUpViews() {
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.xAxis.valueFormatter = formatter
        chart.animateX(ANIMATION_DURATION_GRAPHIC)

        chart.axisLeft.granularity = 0.5f
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(TransactionsInfoViewModel::class.java)

        viewModel.resource.observe(this) {
            it ?: return@observe

            progressBar.isVisible = it.status == Status.LOADING

            if (it.status == Status.SUCCESS) {
                bindChart(it.data!!)
            } else if (it.status == Status.FAILED) {
                Snackbar.make(chart, R.string.common_error, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.retry) { viewModel.loadInfoTransactions() }
                        .show()
            }
        }

        viewModel.loadInfoTransactions()
    }

    /* Owner methods */

    private fun bindChart(items: List<TransactionPerSecond>) {
        val dataSet = LineDataSet(items.map { Entry(it.timestamp, it.numberBitcoins) },
                getString(R.string.transactions_info_label))

        dataSet.setCircleColor(R.color.colorPrimary)
        dataSet.color = ContextCompat.getColor(this, R.color.colorPrimary)
        dataSet.valueTextColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        dataSet.setDrawFilled(true)
        dataSet.fillAlpha = 50
        dataSet.fillColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)

        val data = LineData(dataSet)
        chart.data = data
        //refresh
        chart.invalidate()
    }

}