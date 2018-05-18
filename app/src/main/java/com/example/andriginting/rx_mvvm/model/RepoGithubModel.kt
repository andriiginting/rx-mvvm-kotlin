package com.example.andriginting.rx_mvvm.model

data class RepoGithubModel(val id: Long,
                           val name: String,
                           val description: String,
                           var codeLanguage: String,
                           var stars: Int,
                           val owner: Users)