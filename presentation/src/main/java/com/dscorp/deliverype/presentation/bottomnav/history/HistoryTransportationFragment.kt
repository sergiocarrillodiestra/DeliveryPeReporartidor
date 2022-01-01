package com.dscorp.deliverype.presentation.bottomnav.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.dscorp.deliverype.domain.TransportationOrder
import com.dscorp.deliverype.databinding.HistoryTransportationFragmentTemListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryTransportationFragment : Fragment() {
    private val driverId = "computer"
    private lateinit var binding: HistoryTransportationFragmentTemListBinding

    private val viewModel: HistoryTransportationViewModel by viewModels();

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = HistoryTransportationFragmentTemListBinding.inflate(layoutInflater)

        observeLoader()
        observeHistoryTransportationOrders()
        viewModel.getHistoryOfTransportationOrders(driverId)
        return binding.root
    }

    private fun observeLoader() {
        viewModel.loader.observe(this as LifecycleOwner)
        {
            when (it) {
                true -> {
                    binding.loaderHistory.visibility = View.VISIBLE
                }
                false -> {
                    binding.loaderHistory.visibility = View.GONE
                }
            }
        }
    }

    private fun observeHistoryTransportationOrders() {
        viewModel.historyOrders.observe(this as LifecycleOwner)
        {
            if (it.isSuccess) {
                setupList(it.getOrNull()!!)
            } else {
                it.exceptionOrNull()?.printStackTrace()
            }
        }
    }

    private fun setupList(transportOrdersHistory: List<TransportationOrder>) {
        with(binding.ordersListHistory) {
            layoutManager = LinearLayoutManager(context)
            adapter = MyhistorytransportationRecyclerViewAdapter(transportOrdersHistory) {

            }
        }
    }

}