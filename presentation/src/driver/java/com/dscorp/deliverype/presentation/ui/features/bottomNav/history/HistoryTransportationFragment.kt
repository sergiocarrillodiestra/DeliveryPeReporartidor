package com.dscorp.deliverype.presentation.ui.features.bottomNav.history

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dscorp.deliverype.base.BaseFragment
import com.dscorp.deliverype.databinding.HistoryTransportationFragmentTemListBinding
import com.dscorp.deliverype.domain.entity.Failure
import com.dscorp.deliverype.domain.entity.TransportationOrder
import com.dscorp.deliverype.presentation.features.bottomnav.history.MyhistorytransportationRecyclerViewAdapter
import com.dscorp.deliverype.presentation.ui.features.bottomNav.history.intent.HistoryTransportationIntent
import com.dscorp.deliverype.presentation.ui.features.bottomNav.history.intent.HistoryTransportationOrdersOrchestrator
import com.dscorp.deliverype.presentation.ui.features.bottomNav.history.state.HistoryTransportationState.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryTransportationFragment : BaseFragment<HistoryTransportationFragmentTemListBinding>(),
    HistoryTransportationOrdersOrchestrator.HistoryTransportationCallback {
    private val driverId = "computer"
    override fun getViewBinding() = HistoryTransportationFragmentTemListBinding.inflate(layoutInflater)

    private val viewModel: HistoryTransportationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeUserIntents()
        getHistoryTranspOrders(driverId)
    }

    private fun getHistoryTranspOrders(driverId: String) {
        lifecycleScope.launchWhenCreated {
            viewModel.userIntent.send(HistoryTransportationIntent.FetchHistoryTransportOrders(driverId))
        }
    }

    private fun observeUserIntents() {
        lifecycleScope.launch {
            viewModel.intentState.collect {
                HistoryTransportationOrdersOrchestrator.instance(this@HistoryTransportationFragment)
                    .orchestrate(it)
            }
        }
    }

    private fun setupList( transportOrders: List<TransportationOrder>) {
        with(binding.ordersListHistory) {
            layoutManager = LinearLayoutManager(context)
            adapter = MyhistorytransportationRecyclerViewAdapter(transportOrders) {
            }
        }
    }

    override fun onHistoryTransportationOrdersFetched(orders: List<TransportationOrder>) {
        closeLoadingDialog()
        setupList(orders)
    }

    override fun onLoading() {
        showLoadingDialog()
    }

    override fun onError(error: Failure) {
        closeLoadingDialog()
        handleUseCaseFailureFromBase(error)
    }

}