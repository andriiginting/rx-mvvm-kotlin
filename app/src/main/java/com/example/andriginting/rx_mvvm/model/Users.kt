package com.example.andriginting.rx_mvvm.model

data class Users(val id: Long,
                 val name: String,
                 val url: String,
                 val email: String,
                 var avatar: String)