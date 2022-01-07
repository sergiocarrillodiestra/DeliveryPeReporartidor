package com.dscorp.deliverype.framework.hilt.modules

import com.dscorp.deliverype.domain.repository.TransportationOrdersRepository
import com.dscorp.deliverype.domain.usecases.GetHistoryTransportationOrdersUseCase
import com.dscorp.deliverype.domain.usecases.GetNewTransportationOrdersUseCase
import com.dscorp.deliverype.domain.usecases.GetTakenTransportationOrdersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * Created by Sergio Carrillo Diestra on 6/01/2022.
 * Huacho, Peru.
 * scarrillo.peruapps@gmail.com
 * For Peru Apps
 *
 **/
@Module
@InstallIn(ViewModelComponent::class)
class UseCasesModule {

    @Provides
    fun providesGetNewTransportationOrdersUseCase(transportationOrdersRepository: TransportationOrdersRepository) =
        GetNewTransportationOrdersUseCase(transportationOrdersRepository)

    @Provides
    fun providesGetTakenTransportationOrdersUseCase(transportationOrdersRepository: TransportationOrdersRepository) =
        GetTakenTransportationOrdersUseCase(transportationOrdersRepository)

    @Provides
    fun providesGetHistoryTransportationOrdersUseCase(transportationOrdersRepository: TransportationOrdersRepository) =
        GetHistoryTransportationOrdersUseCase(transportationOrdersRepository)

}