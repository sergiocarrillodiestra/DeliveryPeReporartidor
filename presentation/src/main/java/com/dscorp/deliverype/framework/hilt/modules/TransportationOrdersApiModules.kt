package com.dscorp.deliverype.framework.hilt.modules

import com.dscorp.deliverype.data.network.enpoints.RemoteAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class TransportationOrdersApiModules {

    @Singleton
    @Provides
    fun providesTranspotationOrdersApi(retrofit: Retrofit): RemoteAPI =
        retrofit.create(RemoteAPI::class.java)

}