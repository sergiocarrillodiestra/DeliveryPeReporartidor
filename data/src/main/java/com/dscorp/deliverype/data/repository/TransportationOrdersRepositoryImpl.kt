package com.dscorp.deliverype.data.repository

import com.dscorp.deliverype.data.mappers.ResponseMapper
import com.dscorp.deliverype.data.network.enpoints.RemoteDataSource
import com.dscorp.deliverype.domain.entity.TransportationOrder
import com.dscorp.deliverype.domain.entity.Either
import com.dscorp.deliverype.domain.entity.Failure
import com.dscorp.deliverype.domain.repository.TransportationOrdersRepository

class TransportationOrdersRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val responseMapper: ResponseMapper
) :
    TransportationOrdersRepository {
    override suspend fun getNewTransportationOrders(): Either<Failure, List<TransportationOrder>> {
        return when (val response = remoteDataSource.getNewTransportationOrders()) {
            is Either.Success -> Either.Success(responseMapper.TransportationOrdersResponseToDomain(response.success))
            is Either.Error -> Either.Error(response.error)
        }
    }

    override suspend fun getTakenTransportationOrders(driverId: String): Either<Failure, List<TransportationOrder>> {
        return when (val response = remoteDataSource.getTakenTransportationOrders(driverId)) {
            is Either.Success -> Either.Success(responseMapper.TransportationOrdersResponseToDomain(response.success))
            is Either.Error -> Either.Error(response.error)
        }
    }

    override suspend fun getHistoryOfTransportationOrders(driverId: String): Either<Failure, List<TransportationOrder>> {
        return when (val response = remoteDataSource.getHistoryOfTransportationOrders(driverId)) {
            is Either.Success -> Either.Success(responseMapper.TransportationOrdersResponseToDomain(response.success))
            is Either.Error -> Either.Error(response.error)
        }
    }
}
