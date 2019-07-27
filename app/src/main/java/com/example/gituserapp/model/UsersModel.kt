package com.example.gituserapp.model

object UsersModel {

    data class Result(
        val total_count : Int,
        val incomplete_results : String,
        val items : List<Items>
    )

    data class Items (

        val login : String,
        val id : String,
        val avatar_url : String,
        val url : String,
        val html_url  : String,
        val followers_url : String,
        val repos_url : String,
        val type : String,
        val score : String
    )
}