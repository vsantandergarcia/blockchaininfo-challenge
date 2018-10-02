package com.vsantander.blockchaininfochallenge.presentation.info

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.vsantander.blockchaininfochallenge.R
import com.vsantander.blockchaininfochallenge.extension.observe
import com.vsantander.blockchaininfochallenge.presentation.base.activity.BaseActivity
import javax.inject.Inject

@BaseActivity.Animation(BaseActivity.FADE)
class TransactionsInfoActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: TransactionsInfoViewModel

    /* Activity methods */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_info)
        setUpViews()
        setUpViewModels()
    }

    /* setUp methods */

    private fun setUpViews() {

    }

    private fun setUpViewModels() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(TransactionsInfoViewModel::class.java)

        viewModel.resource.observe(this) {
            it ?: return@observe
        }

        viewModel.loadInfoTransactions()
    }

}