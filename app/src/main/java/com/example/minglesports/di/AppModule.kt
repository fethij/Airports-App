package com.example.minglesports.di

import com.example.minglesports.common.Constants
import com.example.minglesports.data.remote.AirportApi
import com.example.minglesports.data.repository.AirportRepositoryImpl
import com.example.minglesports.data.repository.FlightRepositoryImpl
import com.example.minglesports.domain.repository.AirportRepository
import com.example.minglesports.domain.repository.FlightRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
//This dependencies live as long as our application
@InstallIn(SingletonComponent::class)
object AppModule{

    @Provides
    @Singleton
    fun provideAirportApi(): AirportApi{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AirportApi::class.java)
    }

    @Provides
    //TODO uitleg singelton
    @Singleton
    fun provideairportRepository(api: AirportApi): AirportRepository{
        return AirportRepositoryImpl(api)
    }

    @Provides
    //TODO uitleg singelton
    @Singleton
    fun provideFlightRepository(api: AirportApi): FlightRepository{
        return FlightRepositoryImpl(api)
    }


}