package com.dranoer.giphyapp.di

import android.content.Context
import androidx.room.Room
import com.dranoer.giphyapp.BuildConfig
import com.dranoer.giphyapp.Constants
import com.dranoer.giphyapp.Constants.DATABASE_NAME
import com.dranoer.giphyapp.data.local.GiphyDatabase
import com.dranoer.giphyapp.data.local.LocalDataSource
import com.dranoer.giphyapp.data.remote.NetworkDataSource
import com.dranoer.giphyapp.data.remote.RequestInterceptor
import com.dranoer.giphyapp.data.remote.WebService
import com.dranoer.giphyapp.domain.GiphyRepository
import com.google.gson.GsonBuilder
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
    fun provideApiService(): WebService {

        val gson = GsonBuilder().setLenient().create()

        val httpLogger = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(RequestInterceptor())
            .addInterceptor(httpLogger)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(WebService::class.java)
    }

    @Singleton
    @Provides
    fun provideRepository(
        networkDataSource: NetworkDataSource,
        localDataSource: LocalDataSource,
    ) =
        GiphyRepository(networkDataSource, localDataSource)

    @Singleton
    @Provides
    fun provideRoomInstance(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        GiphyDatabase::class.java,
        DATABASE_NAME
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideDao(db: GiphyDatabase) = db.giphyDao()
}