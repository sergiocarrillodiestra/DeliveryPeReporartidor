package com.dscorp.deliverypedrivers.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dscorp.deliverypedrivers.databinding.TransportationOrdersFragmentItemBinding
import com.dscorp.deliverypedrivers.domain.TransportationOrder

class MyTransportationOrdersRecyclerViewAdapter(
    private val values: List<TransportationOrder>
) : RecyclerView.Adapter<MyTransportationOrdersRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            TransportationOrdersFragmentItemBinding.inflate(
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
            customerPhone.text=item.telefonoCliente
        }


    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: TransportationOrdersFragmentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val customerName: TextView = binding.customerName
        val orderTotalPrice: TextView = binding.orderTotalPrice
        val customerPhone = binding.customerPhones
        val establishmentName = binding.establishmentName
        val deliverySite = binding.deliverySite

        override fun toString(): String {
            return super.toString() + " '" + orderTotalPrice.text + "'"
        }
    }
}