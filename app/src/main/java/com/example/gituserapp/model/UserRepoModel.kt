package com.example.gituserapp.model

object UserRepoModel {

    data class UserRepo(
        val repoList : List<UserRepoModel.Repository>
    )

    data class Repository (

        val id : String,
        val forks : String,
        val description : String,
        val html_url : String,
        val watchers : String,
        val created_at : String,
        val updated_at : String
    )
}