package com.example.gituserapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gituserapp.endpoint.ApiService
import com.example.gituserapp.model.UsersModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DataRepository {


    private val api: ApiService = ApiService.create()
    private val subscriptions = CompositeDisposable()

    fun getUserData(query:String,page:Int): Single<UsersModel.Result> {
        return api.getSearchedUserData(
            query,page
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getSubsequentUserData(query:String,nextPage:Int): MutableLiveData<ArrayList<UsersModel.Items>> {

        val subsequentUserList = MutableLiveData<ArrayList<UsersModel.Items>>()

        var usersList: ArrayList<UsersModel.Items> = ArrayList()
        subscriptions.add(
            api.getSearchedUserData(query,nextPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                            userData: UsersModel.Result ->
                        usersList = ArrayList(userData.items)
                        subsequentUserList.value = usersList
                    }))

        return subsequentUserList
    }
}