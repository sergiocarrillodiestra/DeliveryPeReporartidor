package com.dscorp.deliverype.presentation.features.bottomnav.neworders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dscorp.deliverype.R
import com.dscorp.deliverype.databinding.NewTransportationFragmentOrdersListBinding
import com.dscorp.deliverype.domain.entity.TransportationOrder
import com.dscorp.deliverype.presentation.MyUtil
import com.dscorp.deliverype.presentation.SpacesItemDecoration
import com.dscorp.deliverype.presentation.features.bottomnav.neworders.intent.NewTransportationIntent
import com.dscorp.deliverype.presentation.features.bottomnav.neworders.state.NewTransportationState
import com.dscorp.deliverype.presentation.features.bottomnav.neworders.viewmodel.NewTransportationOrdersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class NewTransportationOrdersFragment : Fragment() {
    private lateinit var bindig: NewTransportationFragmentOrdersListBinding
    private val viewModelNew: NewTransportationOrdersViewModel by viewModels()
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        bindig = NewTransportationFragmentOrdersListBinding.inflate(layoutInflater)
        getNewTransportationOrders()
        observeViewModel()
        return bindig.root
    }

    private fun getNewTransportationOrders() {
        lifecycleScope.launch {
            viewModelNew.userIntent.send(NewTransportationIntent.FetchNewTransportations)
        }
    }

    private fun observeViewModel() {
        with(bindig) {
            lifecycleScope.launch {

                viewModelNew.intentState.collect { newOrdersState ->

                    when (newOrdersState) {
                        is NewTransportationState.Loading -> loader.visibility = View.VISIBLE
                        is NewTransportationState.LoadNewTranspOrders -> {
                            loader.visibility = View.GONE
                            setUpList(ordersList, newOrdersState.transportationsOrders)
                        }
                        is NewTransportationState.Error -> {
                            loader.visibility = View.GONE
                            Toast.makeText(
                                    context, "Error ${newOrdersState.error}", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

            }
        }
    }


    private fun setUpList(
            recyclerView: RecyclerView, transportationOrders: List<TransportationOrder>
    ) {
        with(recyclerView) {
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            addItemDecoration(
                    SpacesItemDecoration(
                            MyUtil.dp2px(
                                    context, activity?.resources!!.getDimension(R.dimen.regular_margin)
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

    }

}