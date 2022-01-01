package com.dscorp.deliverype.domain

data class TransportationOrder(
    val costoDelivery: String,
    val costoTotal: String,
    val courierNotfound: Boolean,
    val detalles: List<Detalle>,
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