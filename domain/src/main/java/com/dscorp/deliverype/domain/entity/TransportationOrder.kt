package com.dscorp.deliverype.domain.entity

data class TransportationOrder(
    val costoDelivery: String,
    val costoTotal: String,
    val productos: List<Producto>,
    val direccionEntrega: String,
    val estado: String,
    val fecha: String,
    val id: String,
    val idEstablecimiento: String,
    val idPedido: String,
    val lugarEntrega: String,
    val cliente: String,
    val establecimiento: String,
    val pagaCon: String,
    val pagoSinVuelto: Boolean,
    val subTotal: String,
    val telefono: String,
    val telefonoAxiliar: String
)