package com.dscorp.deliverype.presentation.features.bottomnav.neworders

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dscorp.deliverype.domain.entity.TransportationOrder
import com.dscorp.deliverype.databinding.NewTransportationOrdersFragmentItemBinding

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
            customerName.text = item.cliente
            orderTotalPrice.text = item.costoTotal
            deliverySite.text = item.lugarEntrega
            establishmentName.text = item.establecimiento
            customerPhone.text = item.telefono
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