package com.nulp.mentor.di

import android.content.Context
import com.nulp.mentor.common.Constants
import com.nulp.mentor.common.PrefService
import com.nulp.mentor.data.remote.Api
import com.nulp.mentor.data.repository.AuthRepositoryImpl
import com.nulp.mentor.data.repository.HomeRepositoryImpl
import com.nulp.mentor.domain.repository.AuthRepository
import com.nulp.mentor.domain.repository.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import okhttp3.OkHttpClient

import okhttp3.logging.HttpLoggingInterceptor

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi(): Api {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }

    @Provides
    @Singleton
    fun providePrefService(@ApplicationContext context: Context): PrefService{
        return PrefService(context)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(api: Api): AuthRepository {
        return AuthRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideHomeRepository(api: Api): HomeRepository{
        return HomeRepositoryImpl(api)
    }

}