package com.dscorp.deliverype.framework.hilt.modules

import com.dscorp.deliverype.data.network.enpoints.TransportationOrderService
import com.dscorp.deliverype.data.TransportationOrdersRepository
import com.dscorp.deliverype.data.TransportationOrdersService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class TransportationOrdersModule {

    @Provides
    fun providesTransportationService(service: TransportationOrderService)=TransportationOrdersService(service)

    @Provides
    fun providesTransportationOrdersRepository(service:TransportationOrdersService) = TransportationOrdersRepository(service)

}