package com.dscorp.deliverypedrivers.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dscorp.deliverypedrivers.databinding.TransportationOrdersFragmentBinding
import com.dscorp.deliverypedrivers.domain.TransportationOrder
import com.dscorp.deliverypedrivers.presentation.transportationOrders.TransportationOrdersViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.recyclerview.widget.DividerItemDecoration
import com.dscorp.deliverypedrivers.R


@AndroidEntryPoint
class TransportationOrdersFragment : Fragment() {
    private lateinit var bindig: TransportationOrdersFragmentBinding
    private val viewModel: TransportationOrdersViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindig = TransportationOrdersFragmentBinding.inflate(layoutInflater)
        observeLoader()
        observeNewTransportationOrders(bindig.ordersList)
        viewModel.getNewTransportationOrders("computer")
        return bindig.root
    }

    private fun observeLoader() {
        viewModel.loader.observe(this as LifecycleOwner, {loaderStatus->
            when (loaderStatus) {
                true -> {bindig.loader.visibility= View.VISIBLE}
                false -> {bindig.loader.visibility= View.GONE}
            }

        })

    }

    private fun observeNewTransportationOrders(recyclerView: RecyclerView) {

        viewModel.newTransportationOrders
            .observe(this as LifecycleOwner, { transportationOrders ->
                if (transportationOrders.getOrNull() != null)
                    setUpList(
                        recyclerView,
                        transportationOrders.getOrNull()!!
                    )
                else {
                    transportationOrders.exceptionOrNull()!!.printStackTrace()
                }
            })

    }

    private fun setUpList(
        recyclerView: RecyclerView,
        transportationOrders: List<TransportationOrder>
    ) {
        with(recyclerView)
        {
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            addItemDecoration(SpacesItemDecoration(MyUtil.dp2px(context,activity?.resources!!.getDimension(R.dimen.regular_margin))))
            layoutManager = LinearLayoutManager(context)
            adapter = MyTransportationOrdersRecyclerViewAdapter(transportationOrders)
        }
    }


}