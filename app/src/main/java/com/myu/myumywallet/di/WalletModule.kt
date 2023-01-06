package com.myu.myumywallet.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.myu.myumywallet.data.remote.WalletService
import com.myu.myumywallet.data.repository.WalletRepository
import com.myu.myumywallet.utils.ApiConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module

object WalletModule {
    @Provides
    @Named("BASE_URL")
    fun provideBaseUrl() = ApiConstants.BASE_URL


    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, @Named("BASE_URL") BASE_URL: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideMovieService(retrofit: Retrofit): WalletService =
        retrofit.create(WalletService::class.java)

    @Provides
    fun provideRepository(api : WalletService) = WalletRepository(api)

}