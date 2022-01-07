package com.dscorp.deliverype.data.network.response

data class TransportationOrderResponse(
    val costoDelivery: String,
    val costoTotal: String,
    val courierNotfound: Boolean,
    val detalles: List<DetalleResponse>,
    val direccionEntrega: String,
    val estado: String,
    val fechaPedido: String,
    val id: String,
    val idEstablecimiento: String,
    val idPedido: String,
    val idUserRepartidor: String,
    val idrep_est: String,
    val lugarEntrega: String,
    val nombreCliente: String,
    val nombreEstablecimiento: String,
    val nombreRepartidor: String,
    val pagaCon: String,
    val pagoSinVuelto: Boolean,
    val subTotal: String,
    val telefonoCliente: String,
    val telefonoReferenciaCliente: String
)