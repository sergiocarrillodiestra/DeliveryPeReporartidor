package com.dscorp.deliverype.presentation.features.bottomnav.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dscorp.deliverype.databinding.HistoryTransportationFragmentTemListBinding
import com.dscorp.deliverype.domain.entity.TransportationOrder
import com.dscorp.deliverype.presentation.features.bottomnav.history.intent.HistoryTransportationIntent
import com.dscorp.deliverype.presentation.features.bottomnav.history.state.HistoryTransportationState
import com.dscorp.deliverype.presentation.features.bottomnav.history.state.HistoryTransportationState.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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
        observeUserIntents()
        getHistoryTranspOrders(driverId)
        return binding.root
    }

    private fun getHistoryTranspOrders(driverId: String) {
        lifecycleScope.launchWhenCreated {
            viewModel.userIntent.send(HistoryTransportationIntent.fetchTakenTransportOrders(driverId))
        }
    }

    private fun observeUserIntents() {
        with(binding)
        {
            lifecycleScope.launch {
                viewModel.intentState.collect {
                    when (it) {
                        is Loading ->loaderHistory.visibility= View.VISIBLE
                        is LoadTransportOrders->{
                            loaderHistory.visibility= View.GONE
                            setupList(ordersListHistory,it.transportOrders)
                        }
                        is Error -> {
                            loaderHistory.visibility = View.GONE
                            Toast.makeText(
                                    context, "Error ${it.message}", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }

        }
    }

    private fun setupList(ordersListHistory: RecyclerView, transportOrders: List<TransportationOrder>) {
        with(binding.ordersListHistory) {
            layoutManager = LinearLayoutManager(context)
            adapter = MyhistorytransportationRecyclerViewAdapter(transportOrders) {

            }
        }
    }


}