package com.dscorp.deliverype.presentation.features.bottomnav.takenorders

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dscorp.deliverype.domain.entity.TransportationOrder
import com.dscorp.deliverype.databinding.TakenTransportationOrdersFragmentItemBinding

class MyTakenTransportationOrderRecyclerViewAdapter(
    private val values: List<TransportationOrder>,
    private val listener: (TransportationOrder) -> Unit
) : RecyclerView.Adapter<MyTakenTransportationOrderRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            TakenTransportationOrdersFragmentItemBinding.inflate(
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

    inner class ViewHolder(binding: TakenTransportationOrdersFragmentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val customerName: TextView = binding.customerNameTakedTransportations
        val orderTotalPrice: TextView = binding.orderTotalPriceTakedTransportations
        val customerPhone = binding.customerPhonesTakedTransportations
        val establishmentName = binding.establishmentNameTakedTransportations
        val deliverySite = binding.deliverySiteTakedTransportations
        val root = binding.root
        override fun toString(): String {
            return super.toString() + " '" + orderTotalPrice.text + "'"
        }
    }

}