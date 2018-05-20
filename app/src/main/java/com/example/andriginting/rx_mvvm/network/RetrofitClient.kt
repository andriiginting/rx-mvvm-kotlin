package com.example.andriginting.rx_mvvm.network

import android.util.Log
import com.example.andriginting.rx_mvvm.BuildConfig
import com.example.andriginting.rx_mvvm.Const.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {

         private fun getClient(): Retrofit {

            return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(defaultHTTPClient())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }

        private fun defaultHTTPClient(): OkHttpClient {
            return OkHttpClient.Builder()
                    .addInterceptor(defaultLoggingInterceptor())
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build()
        }

        private fun defaultLoggingInterceptor(): HttpLoggingInterceptor {
            val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { pesan ->
                Log.d("Error", pesan.toString())
            }) //membuat interceptor yg berfungsi untuk menerima response log http

            interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

            return interceptor
        }

        fun provideApi(): RetrofitInterface{
            return getClient().create(RetrofitInterface::class.java)
        }
    }
