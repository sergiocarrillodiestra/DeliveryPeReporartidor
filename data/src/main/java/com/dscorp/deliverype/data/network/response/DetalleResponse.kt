package com.dscorp.deliverype.data.network.response

data class DetalleResponse(
    val cantidad: Int,
    val categoriaDeComida: String,
    val idProducto: String,
    val nombreProducto: String,
    val nota: String,
    val precioVenta: Int
)