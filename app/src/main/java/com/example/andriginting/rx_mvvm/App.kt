package com.example.andriginting.rx_mvvm

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers


class App: Application() {
    private var defaultSubscribeScheduler: Scheduler? = null

    override fun onCreate() {
        super.onCreate()

        instance = this
    }

    fun checkIfHasNetwork(): Boolean{
        val conectionManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = conectionManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    //membuat job scheduler
    fun defaulJobScheduler(): Scheduler?{
        if(defaultSubscribeScheduler == null ){
            defaultSubscribeScheduler = Schedulers.io()
        }
        return defaultSubscribeScheduler
    }

     companion object {
         var instance: App? = null
         private set //sebagai setter instance app


         //untuk cek apakah user punya koneksi internet
         fun hasNetworkConnection(): Boolean{
            return instance!!.checkIfHasNetwork()
         }

     }
}