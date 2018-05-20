package com.example.andriginting.rx_mvvm.viewmodel

import com.example.andriginting.rx_mvvm.model.RepoGithubModel

interface DataListener {
    fun onRepositoryChanged(repos: List<RepoGithubModel>)
}