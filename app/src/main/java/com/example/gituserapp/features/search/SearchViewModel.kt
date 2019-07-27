package com.example.gituserapp.features.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gituserapp.model.UsersModel
import com.example.gituserapp.repository.DataRepository
import io.reactivex.Single

class SearchViewModel: ViewModel() {

    var dataRepository: DataRepository

    init{
        dataRepository = DataRepository()
    }

    fun getUserData(searchedString:String, page:Int): Single<UsersModel.Result> {
        return dataRepository.getUserData(searchedString,page)
    }

    fun getSubsequentUserData(searchedString:String, nextPage:Int): MutableLiveData<ArrayList<UsersModel.Items>> {
        return dataRepository.getSubsequentUserData(searchedString,nextPage)
    }
}