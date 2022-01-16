package com.dscorp.deliverype.presentation.ui.features.bottomNav.neworders

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
import com.dscorp.deliverype.databinding.NewTransportationFragmentOrdersListBinding
import com.dscorp.deliverype.domain.entity.Failure
import com.dscorp.deliverype.domain.entity.TransportationOrder
import com.dscorp.deliverype.presentation.MyUtil
import com.dscorp.deliverype.presentation.SpacesItemDecoration
import com.dscorp.deliverype.presentation.ui.features.bottomNav.neworders.intent.NewTransportationIntents
import com.dscorp.deliverype.presentation.ui.features.bottomNav.neworders.state.NewTransportationIntentStateOrchestrator
import com.dscorp.deliverype.presentation.ui.features.bottomNav.neworders.viewmodel.NewTransportationOrdersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewTransportationOrdersFragment : BaseFragment<NewTransportationFragmentOrdersListBinding>(),
    NewTransportationIntentStateOrchestrator.NewTransportationCallback {
    private lateinit var intentStateorchestator: NewTransportationIntentStateOrchestrator
    override fun getViewBinding() =
        NewTransportationFragmentOrdersListBinding.inflate(layoutInflater)

    private val viewModelNew: NewTransportationOrdersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        observeIntentSteteChanges()
        getNewTransportationOrders()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun observeIntentSteteChanges() {
        //INICIALIZA EL MAINTENTCONFIG CON EL CALLBACK IMPLEMENTADO EN EL MAINACTIVITY
        intentStateorchestator = NewTransportationIntentStateOrchestrator.instance(this)
        viewModelNew.apply {
            lifecycleScope.launchWhenCreated {
                intentIntentStates.collect {
                    intentStateorchestator.orchestrate(it)
                }
            }
        }
    }

    private fun getNewTransportationOrders() {
        lifecycleScope.launch {
            viewModelNew.userIntent.send(NewTransportationIntents.FetchNewTransportations)
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
                        context, activity?.resources!!.getDimension(R.dimen.dimen16)
                    )
                )
            )

            layoutManager = LinearLayoutManager(context)
            adapter = MyNewTransportationOrdersRecyclerViewAdapter(transportationOrders) {
                selectItemEvent(it)
            }

            val swipeHandler = object : SwipeToTakeOrderCallback() {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    selectItemEvent(transportationOrders[viewHolder.absoluteAdapterPosition])
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

    override fun onNewTransportationOrdersFetched(transportationsOrders: List<TransportationOrder>) {
        closeLoadingDialog()
        setUpList(binding.ordersList, transportationsOrders)
    }

    override fun onLoading() {
        showLoadingDialog()
    }

    override fun onError(error: Failure) {
        closeLoadingDialog()
        handleUseCaseFailureFromBase(error)
    }

}