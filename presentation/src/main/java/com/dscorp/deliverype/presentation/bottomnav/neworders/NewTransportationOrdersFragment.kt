package com.dscorp.deliverype.presentation.bottomnav.neworders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dscorp.deliverype.domain.TransportationOrder
import com.dscorp.deliverype.presentation.MyUtil
import com.dscorp.deliverype.presentation.SpacesItemDecoration
import com.dscorp.deliverype.R
import com.dscorp.deliverype.databinding.NewTransportationFragmentOrdersListBinding
import com.dscorp.deliverype.presentation.bottomnav.neworders.state.NewTransportationState
import com.dscorp.deliverype.presentation.bottomnav.neworders.viewmodel.NewTransportationOrdersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class NewTransportationOrdersFragment : Fragment() {
    private lateinit var bindig: NewTransportationFragmentOrdersListBinding
    private val viewModelNew: NewTransportationOrdersViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindig = NewTransportationFragmentOrdersListBinding.inflate(layoutInflater)
//        observeLoader()
//        observeNewTransportationOrders(bindig.ordersList)
//        viewModelNew.getNewTransportationOrders()
        observeViewModel()
        return bindig.root
    }

    private fun observeViewModel() {

        lifecycleScope.launch{
            viewModelNew.transportationOrderState.collect {
                newOrdersState->
                when (newOrdersState) {
                   is NewTransportationState.Loading  ->  bindig.loader.visibility = View.VISIBLE
                   is NewTransportationState.LoadNewTransportations->{
                      bindig.loader.visibility= View.GONE
                       newOrdersState.transportationsOrders
                   }
                    is NewTransportationState.Error->{
                        bindig.loader.visibility= View.GONE
                        Toast.makeText(context, "Error ${newOrdersState.error}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun observeLoader() {
        viewModelNew.loader.observe(this as LifecycleOwner, { loaderStatus ->
            when (loaderStatus) {
                true -> {
                    bindig.loader.visibility = View.VISIBLE
                }
                false -> {
                    bindig.loader.visibility = View.GONE
                }
            }

        })

    }

    private fun observeNewTransportationOrders(recyclerView: RecyclerView) {

        viewModelNew.newTransportationOrders
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
            addItemDecoration(
                SpacesItemDecoration(
                    MyUtil.dp2px(
                        context,
                        activity?.resources!!.getDimension(R.dimen.regular_margin)
                    )
                )
            )

            layoutManager = LinearLayoutManager(context)
            adapter = MyNewTransportationOrdersRecyclerViewAdapter(transportationOrders) {
                listItemEvent()
            }

            val swipeHandler = object : SwipeToTakeOrderCallback() {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    listItemEvent()
                    (adapter as RecyclerView.Adapter).notifyItemChanged(viewHolder.absoluteAdapterPosition)
                }
            }

            val itemTouchHelper = ItemTouchHelper(swipeHandler)
            itemTouchHelper.attachToRecyclerView(this)

        }
    }

    private fun listItemEvent() {
//
//        val action =
//            NewTransportationOrdersFragmentDirections.actionTransportationOrdersTakenFragment()
//        findNavController().navigate(action)
    }


}