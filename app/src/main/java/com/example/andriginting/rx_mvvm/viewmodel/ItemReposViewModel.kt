package com.example.andriginting.rx_mvvm.viewmodel

import android.arch.lifecycle.ViewModel
import android.content.Context
import android.view.View
import android.databinding.BaseObservable
import com.example.andriginting.rx_mvvm.model.RepoGithubModel

class ItemReposViewModel(var context: Context, var repos: RepoGithubModel): BaseObservable(),viewmodel {
    companion object {
        fun setDataToRepoModel(itemReposViewModel: ItemReposViewModel,repos: RepoGithubModel){
            itemReposViewModel.repos = repos
            itemReposViewModel.notifyChange()
        }
    }

    fun getName(): String?{
        return repos.name
    }

    fun getDescription(): String?{
        return repos.description
    }

    fun onItemClick(v: View){

    }

    override fun destroyView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}