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

    //logic to sort users based on their score in ascending order
    fun sortRankAsc(usersList:ArrayList<UsersModel.Items>): MutableLiveData<ArrayList<UsersModel.Items>>{
        val sortedUsersList = MutableLiveData<ArrayList<UsersModel.Items>>()
        var sortedList = usersList.sortedWith(compareBy({ it.score }))
        sortedUsersList.value = ArrayList(sortedList)
        return sortedUsersList
    }

    //logic to sort users based on their score in descending order
    fun sortRankDesc(usersList:ArrayList<UsersModel.Items>): MutableLiveData<ArrayList<UsersModel.Items>>{
        val sortedUsersList = MutableLiveData<ArrayList<UsersModel.Items>>()
        var sortedList = usersList.sortedWith(compareByDescending { it.score })
        sortedUsersList.value = ArrayList(sortedList)
        return sortedUsersList
    }

    //logic to sort users based on their name in ascending order
    fun sortNameAsc(usersList:ArrayList<UsersModel.Items>): MutableLiveData<ArrayList<UsersModel.Items>>{
        val sortedUsersList = MutableLiveData<ArrayList<UsersModel.Items>>()
        var sortedList = usersList.sortedWith(compareBy { it.login })
        sortedUsersList.value = ArrayList(sortedList)
        return sortedUsersList
    }

    //logic to sort users based on their score in descending order
    fun sortNameDesc(usersList:ArrayList<UsersModel.Items>): MutableLiveData<ArrayList<UsersModel.Items>>{
        val sortedUsersList = MutableLiveData<ArrayList<UsersModel.Items>>()
        var sortedList = usersList.sortedWith(compareByDescending { it.login })
        sortedUsersList.value = ArrayList(sortedList)
        return sortedUsersList
    }
}