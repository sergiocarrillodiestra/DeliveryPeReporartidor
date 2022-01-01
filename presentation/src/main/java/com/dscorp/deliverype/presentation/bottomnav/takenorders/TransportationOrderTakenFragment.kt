package com.dscorp.deliverype.presentation.bottomnav.takenorders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dscorp.deliverype.R
import com.dscorp.deliverype.databinding.TakenTransportationFragmentOrdersListBinding
import com.dscorp.deliverype.presentation.MyUtil
import com.dscorp.deliverype.presentation.SpacesItemDecoration
import dagger.hilt.android.AndroidEntryPoint


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
        observeLoader()
        viewmodel.getTakenTransportationOrders(driverId)
        observeTakenTransportationOrders()
//        // Set the adapter
//        if (view is RecyclerView) {
//            with(view) {
//                layoutManager = when {
//                    columnCount <= 1 -> LinearLayoutManager(context)
//                    else -> GridLayoutManager(context, columnCount)
//                }
//                adapter = MyTakenTransportationOrderRecyclerViewAdapter(PlaceholderContent.ITEMS)
//            }
//        }
        return binding.root
    }

    private fun observeLoader() {
        viewmodel.loader.observe(this as LifecycleOwner) {
            when (it) {
                true -> {
                    binding.loaderTakedTransportations.visibility = View.VISIBLE
                }
                false -> binding.loaderTakedTransportations.visibility = View.GONE
            }

        }
    }

    private fun observeTakenTransportationOrders() {

        viewmodel.takedTransportationOrders.observe(this as LifecycleOwner) { result ->
            if (result.getOrNull() != null) {

                with(binding.ordersListTakedTransportations) {


                    layoutManager = LinearLayoutManager(context)

                    adapter = MyTakenTransportationOrderRecyclerViewAdapter(result.getOrNull()!!) {

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


            } else result.exceptionOrNull()?.printStackTrace()
        }
    }

}