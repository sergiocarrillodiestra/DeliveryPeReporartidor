package com.dscorp.deliverype.presentation.features.bottomnav.takenorders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dscorp.deliverype.R
import com.dscorp.deliverype.databinding.TakenTransportationFragmentOrdersListBinding
import com.dscorp.deliverype.domain.entity.TransportationOrder
import com.dscorp.deliverype.presentation.MyUtil
import com.dscorp.deliverype.presentation.SpacesItemDecoration
import com.dscorp.deliverype.presentation.features.bottomnav.neworders.state.NewTransportationState
import com.dscorp.deliverype.presentation.features.bottomnav.takenorders.intent.TakenTransportationIntent
import com.dscorp.deliverype.presentation.features.bottomnav.takenorders.state.TakenTransportationState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TransportationOrderTakenFragment : Fragment() {

    private val driverId = "computer"
    private lateinit var binding: TakenTransportationFragmentOrdersListBinding
    private val viewmodel: TakenTransportationViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = TakenTransportationFragmentOrdersListBinding.inflate(layoutInflater)
        observeUserIntent()
        getTakenTranspOrders()
        return binding.root
    }

    private fun getTakenTranspOrders() {
        lifecycleScope.launchWhenCreated {
            viewmodel.userIntent.send(TakenTransportationIntent.fetchTakenTransportOrders(driverId))
        }
    }

    private fun observeUserIntent() {
        with(binding)
        {
            lifecycleScope.launch {
                viewmodel.intentState.collect {
                    when (it) {
                        is TakenTransportationState.Loading -> loaderTakedTransportations.visibility = View.VISIBLE
                        is TakenTransportationState.LoadTransportOrders -> {
                            loaderTakedTransportations.visibility = View.GONE
                            setupList(ordersListTakedTransportations, it.transportOrders)
                        }
                        is Error -> {
                            loaderTakedTransportations.visibility = View.GONE
                            Toast.makeText(
                                    context, "Error ${it.message}", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                }
            }
        }
    }

    private fun setupList(rv: RecyclerView, transportOrders: List<TransportationOrder>) {
        with(rv) {
            layoutManager = LinearLayoutManager(context)

            adapter = MyTakenTransportationOrderRecyclerViewAdapter(transportOrders) {

                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                addItemDecoration(
                        SpacesItemDecoration(
                                MyUtil.dp2px(
                                        context,
                                        activity?.resources!!.getDimension(R.dimen.regular_margin)
                                )
                        )
                )

            }
        }

    }

}