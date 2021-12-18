package com.dscorp.deliverypedrivers.framework.hilt.modules

import com.dscorp.deliverypedrivers.data.TransportationOrderAPI
import com.dscorp.deliverypedrivers.data.TransportationOrdersRepository
import com.dscorp.deliverypedrivers.data.TransportationOrdersService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class TransportationOrdersModule {

    @Provides
    fun providesTransportationService(api:TransportationOrderAPI)=TransportationOrdersService(api)

    @Provides
    fun providesTransportationOrdersRepository(service:TransportationOrdersService) = TransportationOrdersRepository(service)

}