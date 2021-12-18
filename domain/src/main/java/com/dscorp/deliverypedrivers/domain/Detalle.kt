package com.dscorp.deliverypedrivers.domain

data class Detalle(
    val cantidad: Int,
    val categoriaDeComida: String,
    val idProducto: String,
    val nombreProducto: String,
    val nota: String,
    val precioVenta: Int
)