package com.example.firebasecrudapplication.models

data class Quize(
    var id:String="",
    var title:String="",
    var questions:MutableMap<String,Questions> = mutableMapOf()
)