package com.example.gituserapp.features.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gituserapp.model.UserRepoModel
import com.example.gituserapp.repository.DataRepository

class UserDetailsViewModel: ViewModel() {

    var dataRepository: DataRepository

    init{
        dataRepository = DataRepository()
    }

    fun getUserRepo(loginName:String): MutableLiveData<ArrayList<UserRepoModel.Repository>> {
        return dataRepository.getRepoData(loginName)
    }
}