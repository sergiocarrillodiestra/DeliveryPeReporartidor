package com.dscorp.deliverype.presentation.ui.features.bottomNav.takenorders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dscorp.deliverype.R
import com.dscorp.deliverype.base.BaseFragment
import com.dscorp.deliverype.databinding.TakenTransportationFragmentOrdersListBinding
import com.dscorp.deliverype.domain.entity.Failure
import com.dscorp.deliverype.domain.entity.TransportationOrder
import com.dscorp.deliverype.presentation.MyUtil
import com.dscorp.deliverype.presentation.SpacesItemDecoration
import com.dscorp.deliverype.presentation.features.bottomnav.takenorders.MyTakenTransportationOrderRecyclerViewAdapter
import com.dscorp.deliverype.presentation.ui.features.bottomNav.neworders.SwipeToTakeOrderCallback
import com.dscorp.deliverype.presentation.ui.features.bottomNav.takenorders.intent.TakenTransportationIntent
import com.dscorp.deliverype.presentation.ui.features.bottomNav.takenorders.state.TakenTransportationStateOrchestrator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TransportationOrderTakenFragment : BaseFragment<TakenTransportationFragmentOrdersListBinding>(),
    TakenTransportationStateOrchestrator.TakenTransportationCallback {

    private val driverId = "computer"
    override fun getViewBinding() = TakenTransportationFragmentOrdersListBinding.inflate(layoutInflater)
    private val viewModel: TakenTransportationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        observeUserIntent()
        getTakenTranspOrders()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun getTakenTranspOrders() {
        lifecycleScope.launchWhenCreated {
            viewModel.userIntent.send(TakenTransportationIntent.FetchTakenTransportationOrders(driverId))
        }
    }

    private fun observeUserIntent() {
        lifecycleScope.launch {
            viewModel.intentState.collect {
                TakenTransportationStateOrchestrator.instance(this@TransportationOrderTakenFragment)
                    .orchestrate(it)
            }
        }
    }

    private fun setupList(rv: RecyclerView, transportOrders: List<TransportationOrder>) {
        with(rv) {
            layoutManager = LinearLayoutManager(context)
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            addItemDecoration(
                SpacesItemDecoration(
                    MyUtil.dp2px(
                        context,
                        activity?.resources!!.getDimension(R.dimen.regular_margin)
                    )
                )
            )
            adapter = MyTakenTransportationOrderRecyclerViewAdapter(transportOrders) {
                selectItemEvent(it)
            }

            val swipeHandler = object : SwipeToTakeOrderCallback() {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    selectItemEvent(transportOrders[viewHolder.absoluteAdapterPosition])
                    (adapter as RecyclerView.Adapter).notifyItemChanged(viewHolder.absoluteAdapterPosition)
                }
            }

            val itemTouchHelper = ItemTouchHelper(swipeHandler)
            itemTouchHelper.attachToRecyclerView(this)
        }
    }

    private fun selectItemEvent(item: TransportationOrder) {
        showConfirmDialog(getString(R.string.wantToTakeOrder)) {
            showToast("item selected ${item.cliente}")
        }
    }

    override fun onTakenTransportationFetched(transportOrders: List<TransportationOrder>) {
        closeLoadingDialog()

        setupList(binding.ordersListTakedTransportations, transportOrders)
    }

    override fun onLoading() {
        showLoadingDialog()
    }

    override fun onError(error: Failure) {
        closeLoadingDialog()
        handleUseCaseFailureFromBase(error)
    }

}