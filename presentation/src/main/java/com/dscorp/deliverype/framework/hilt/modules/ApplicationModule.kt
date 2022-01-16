package com.dscorp.deliverype.framework.hilt.modules

import android.content.Context
import com.dscorp.deliverype.Constants
import com.dscorp.deliverype.data.mappers.ResponseMapper
import com.dscorp.deliverype.data.mappers.ResponseMapperImpl
import com.dscorp.deliverype.data.network.utils.ConnectionUtils
import com.dscorp.deliverype.data.network.utils.ConnectionUtilsImpl
import com.dscorp.deliverype.data.network.utils.NetworkUtils
import com.google.gson.GsonBuilder
import com.jakewharton.espresso.OkHttp3IdlingResource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

val client = OkHttpClient()
val idlingResource = OkHttp3IdlingResource.create("okhttp", client)

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {


    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
//        val myRequestInterceptor = RequestInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
//        httpClient.addInterceptor(myRequestInterceptor)
        val gson = GsonBuilder()
//            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .create()

        return Retrofit.Builder()
            .baseUrl(Constants.API_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }


    @Singleton
    @Provides
    fun providesConnectionUtils(@ApplicationContext appContext: Context): ConnectionUtils =
        ConnectionUtilsImpl(appContext)

    @Singleton
    @Provides
    fun providesNetworkUtils(connectionUtils: ConnectionUtils): NetworkUtils =
        NetworkUtils(connectionUtils)

    @Singleton
    @Provides
    fun providesResponseMapper(): ResponseMapper =
        ResponseMapperImpl()


}