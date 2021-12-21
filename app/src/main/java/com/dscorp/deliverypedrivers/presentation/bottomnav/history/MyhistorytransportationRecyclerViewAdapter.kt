package com.dscorp.deliverypedrivers.presentation.bottomnav.history

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dscorp.deliverypedrivers.databinding.HistoryTransportationFragmentItemBinding
import com.dscorp.deliverypedrivers.domain.TransportationOrder

class MyhistorytransportationRecyclerViewAdapter(
    private val values: List<TransportationOrder>,
    private val listener: (TransportationOrder) -> Unit
) : RecyclerView.Adapter<MyhistorytransportationRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            HistoryTransportationFragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        with(holder)
        {
            customerName.text = item.nombreCliente
            orderTotalPrice.text = item.costoTotal
            deliverySite.text = item.lugarEntrega
            establishmentName.text = item.nombreEstablecimiento
            customerPhone.text = item.telefonoCliente
            root.setOnClickListener {
                listener(item)
            }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: HistoryTransportationFragmentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val customerName: TextView = binding.customerNameHistoryTransportation
        val orderTotalPrice: TextView = binding.orderTotalPriceHistoryTransportation
        val customerPhone = binding.customerPhonesSiteHistoryTransportation
        val establishmentName = binding.establishmentNameSiteHistoryTransportation
        val deliverySite = binding.customerDeliverySiteHistoryTransportation
        val root = binding.root
        override fun toString(): String {
            return super.toString() + " '" + orderTotalPrice.text + "'"
        }
    }

}