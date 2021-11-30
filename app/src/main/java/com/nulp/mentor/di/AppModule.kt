package com.nulp.mentor.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.nulp.mentor.common.Constants
import com.nulp.mentor.common.PrefService
import com.nulp.mentor.data.local.NULPDatabase
import com.nulp.mentor.data.remote.Api
import com.nulp.mentor.data.repository.*
import com.nulp.mentor.domain.repository.*
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
    fun providePrefService(@ApplicationContext context: Context): PrefService {
        return PrefService(context)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(api: Api): AuthRepository {
        return AuthRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideHomeRepository(api: Api): HomeRepository {
        return HomeRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideNotificationRepository(database: NULPDatabase): NotificationRepository {
        return NotificationRepositoryImpl(provideApi(), database.notificationDao())
    }

    @Provides
    @Singleton
    fun provideAccountRepository(): AccountRepository {
        return AccountRepositoryImpl(provideApi())
    }

    @Provides
    @Singleton
    fun provideSubjectRepository(): SubjectRepository {
        return SubjectRepositoryImpl(provideApi())
    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application): NULPDatabase {
        return Room.databaseBuilder(app, NULPDatabase::class.java, "nulp_database").build()
    }
}