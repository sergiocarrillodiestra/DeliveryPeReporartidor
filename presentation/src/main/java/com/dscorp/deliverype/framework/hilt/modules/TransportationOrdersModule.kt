package com.dscorp.deliverype.framework.hilt.modules

import com.dscorp.deliverype.data.mappers.ResponseMapper
import com.dscorp.deliverype.data.network.enpoints.RemoteAPI
import com.dscorp.deliverype.data.network.enpoints.RemoteDataSource
import com.dscorp.deliverype.data.network.enpoints.RemoteDataSourceImpl
import com.dscorp.deliverype.data.network.utils.NetworkUtils
import com.dscorp.deliverype.data.repository.TransportationOrdersRepositoryImpl
import com.dscorp.deliverype.domain.repository.TransportationOrdersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class TransportationOrdersModule {

    @Provides
    fun providesRemoteDataSource(
        remoteAPI: RemoteAPI,
        networkUtils: NetworkUtils
    ): RemoteDataSource = RemoteDataSourceImpl(remoteAPI, networkUtils)

    @Provides
    fun providesTransportationOrdersRepository(
        remoteDataSource: RemoteDataSource,
        responseMapper: ResponseMapper
    ):TransportationOrdersRepository =
        TransportationOrdersRepositoryImpl(remoteDataSource, responseMapper)

}