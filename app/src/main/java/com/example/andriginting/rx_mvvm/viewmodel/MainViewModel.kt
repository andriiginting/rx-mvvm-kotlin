package com.example.andriginting.rx_mvvm.viewmodel

import android.content.Context
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.view.View
import com.example.andriginting.rx_mvvm.App
import com.example.andriginting.rx_mvvm.R
import com.example.andriginting.rx_mvvm.model.RepoGithubModel
import com.example.andriginting.rx_mvvm.network.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MainViewModel(var context: Context?, var dataListener: DataListener?): viewmodel {

    //membuat data binding dan observable object
    var infoMessageVisibility: ObservableInt
    var progressVisibility: ObservableInt
    var recyclerVisibility: ObservableInt
    var searchButtonVisibility: ObservableInt
    var infoMessage: ObservableField<String>

    var disposable: DisposableObserver<List<RepoGithubModel>>? = null
    var repositories: List<RepoGithubModel>? = null

    lateinit var edtTextUsernameValue: String

    //init observable object
    init {
        infoMessageVisibility = ObservableInt(View.VISIBLE)
        progressVisibility = ObservableInt(View.INVISIBLE)
        recyclerVisibility = ObservableInt(View.INVISIBLE)
        searchButtonVisibility = ObservableInt(View.VISIBLE)
        infoMessage = ObservableField(context!!.getString(R.string.default_info))
    }

    override fun destroyView() {
        if(!disposable!!.isDisposed) disposable!!.dispose()

        disposable = null
        context = null
        dataListener = null
    }

    fun fetchMyRepository(username: String){
        //set progres, recycler,info
        progressVisibility.set(View.VISIBLE)
        recyclerVisibility.set(View.INVISIBLE)
        infoMessageVisibility.set(View.INVISIBLE)

        disposable = RetrofitClient().provideApi()
                .getRepoBasedOnUsername(username = username)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(App.instance?.defaulJobScheduler())
                .subscribeWith(LoadOperationObserver())
    }

    fun onSearch(view: View){
        fetchMyRepository(edtTextUsernameValue)
    }


    //inner class untuk load repo dari github
    inner class LoadOperationObserver: DisposableObserver<List<RepoGithubModel>>(){
        override fun onComplete() {
            repositories?.let { dataListener?.onRepositoryChanged(it) }

            progressVisibility.set(View.VISIBLE)
            if (repositories!!.isNotEmpty()){
                recyclerVisibility.set(View.VISIBLE)
            }else{
                infoMessage.set(context?.getString(R.string.info_not_found))
                infoMessageVisibility.set(View.VISIBLE)
            }
        }

        override fun onNext(value: List<RepoGithubModel>?) {
            this@MainViewModel.repositories = value
        }

        override fun onError(e: Throwable?) {
            progressVisibility.set(View.INVISIBLE)
            infoMessage.set(context?.getString(R.string.info_not_found))
            infoMessageVisibility.set(View.VISIBLE)

        }
    }

}