package com.dscorp.deliverypedrivers.presentation.bottomnav.neworders

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dscorp.deliverypedrivers.databinding.NewTransportationOrdersFragmentItemBinding
import com.dscorp.deliverypedrivers.domain.TransportationOrder

class MyNewTransportationOrdersRecyclerViewAdapter(
    private val values: List<TransportationOrder>,
    private val listener: (TransportationOrder) -> Unit
) : RecyclerView.Adapter<MyNewTransportationOrdersRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            NewTransportationOrdersFragmentItemBinding.inflate(
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

    inner class ViewHolder(binding: NewTransportationOrdersFragmentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val customerName: TextView = binding.customerName
        val orderTotalPrice: TextView = binding.orderTotalPrice
        val customerPhone = binding.customerPhones
        val establishmentName = binding.establishmentName
        val deliverySite = binding.deliverySite
        val root = binding.root
        override fun toString(): String {
            return super.toString() + " '" + orderTotalPrice.text + "'"
        }
    }
}