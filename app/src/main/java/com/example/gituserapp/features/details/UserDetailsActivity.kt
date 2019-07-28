package com.example.gituserapp.features.details

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gituserapp.R
import com.example.gituserapp.features.search.SearchViewModel
import com.example.gituserapp.features.search.UserListAdapter
import com.example.gituserapp.model.UserRepoModel

class UserDetailsActivity : AppCompatActivity() {

    //local declarations
    private lateinit var loginName : String
    private lateinit var profileURL : String
    private lateinit var mRepoListAdapter: RepoListAdapter

    //other declarations
    private lateinit var mUserDetailsViewModel: UserDetailsViewModel

    //UI declarations
    private lateinit var repositoryRecyclerView: RecyclerView
    private lateinit var mLayoutManager : LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_detail)

        repositoryRecyclerView = findViewById(R.id.repository_recyclerView)

        loginName = intent.getStringExtra("loginId")
        profileURL = intent.getStringExtra("profileUrl")

        mUserDetailsViewModel = ViewModelProviders.of(this)[UserDetailsViewModel::class.java] // ViewModel and livedata is used.

        mUserDetailsViewModel.getUserRepo(loginName).observe(this, Observer<ArrayList<UserRepoModel.Repository>> { repositoryList ->
            // Update the cached copy of the words in the adapter.
            displayRepoList(repositoryList)
        })
    }

    private fun displayRepoList(repositoryList: ArrayList<UserRepoModel.Repository>) {

        mRepoListAdapter = RepoListAdapter(repositoryList)
        mLayoutManager = LinearLayoutManager(this)
        repositoryRecyclerView.setLayoutManager(mLayoutManager)
        repositoryRecyclerView.setItemAnimator(DefaultItemAnimator())
        repositoryRecyclerView.setAdapter(mRepoListAdapter)
    }
}