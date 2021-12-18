package com.dscorp.deliverypedrivers.domain

data class Repartidor(
    private var id: String,
    private val pedidosEnCurso: Int,
    private val trabajando: Boolean,
    private val nombre: String,
) {

}