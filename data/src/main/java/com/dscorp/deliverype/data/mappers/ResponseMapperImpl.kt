package com.dscorp.deliverype.data.mappers

import com.dscorp.deliverype.data.network.response.DetalleResponse
import com.dscorp.deliverype.data.network.response.TransportationOrderResponse
import com.dscorp.deliverype.domain.entity.Producto
import com.dscorp.deliverype.domain.entity.TransportationOrder

/**
 * Created by Sergio Carrillo Diestra on 6/01/2022.
 * Huacho, Peru.
 * scarrillo.peruapps@gmail.com
 * Peru Apps
 *
 **/
class ResponseMapperImpl : ResponseMapper {
    override fun TransportationOrdersResponseToDomain(list: List<TransportationOrderResponse>): List<TransportationOrder> {
        return list.map {
            TransportationOrder(
                costoDelivery = it.costoDelivery,
                costoTotal = it.costoTotal,
                productos = convertData(it.detalles),
                direccionEntrega = it.direccionEntrega,
                estado = it.estado,
                fecha = it.fechaPedido,
                id = it.id,
                idEstablecimiento = it.idEstablecimiento,
                idPedido = it.idPedido,
                lugarEntrega = it.lugarEntrega,
                cliente = it.nombreCliente,
                establecimiento = it.nombreEstablecimiento,
                pagaCon = it.pagaCon,
                pagoSinVuelto = it.pagoSinVuelto,
                subTotal = it.subTotal,
                telefono = it.telefonoCliente,
                telefonoAxiliar = it.telefonoReferenciaCliente
            )
        }

    }

    fun convertData(detalles: List<DetalleResponse>): List<Producto> {
        val mappedArray: ArrayList<Producto> = ArrayList()
        for (detalle in detalles) {
            mappedArray.add(
                Producto(
                    cantidad = detalle.cantidad,
                    id = detalle.idProducto,
                    nombre = detalle.nombreProducto,
                    precio = detalle.precioVenta
                )
            )
        }
        return mappedArray
    }
}