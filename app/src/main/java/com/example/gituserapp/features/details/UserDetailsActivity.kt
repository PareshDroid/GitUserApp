package com.example.gituserapp.features.details

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gituserapp.R
import com.example.gituserapp.features.search.UserListAdapter
import com.example.gituserapp.features.webpage.RepositoryWebPageActivity
import com.example.gituserapp.model.UserRepoModel

class UserDetailsActivity : AppCompatActivity(), RepoListAdapter.Listener {


    //local declarations
    private lateinit var loginName : String
    private lateinit var profileURL : String
    private lateinit var mRepoListAdapter: RepoListAdapter


    //other declarations
    private lateinit var mUserDetailsViewModel: UserDetailsViewModel

    //UI declarations
    private lateinit var repositoryRecyclerView: RecyclerView
    private lateinit var mLayoutManager : LinearLayoutManager
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_detail)

        repositoryRecyclerView = findViewById(R.id.repository_recyclerView)
        progressBar = findViewById(R.id.progressBar)

        loginName = intent.getStringExtra("loginId")
        profileURL = intent.getStringExtra("profileUrl")

        mUserDetailsViewModel = ViewModelProviders.of(this)[UserDetailsViewModel::class.java] // ViewModel and livedata is used.

        progressBar.visibility = View.VISIBLE

        mUserDetailsViewModel.getUserRepo(loginName).observe(this, Observer<ArrayList<UserRepoModel.Repository>> { repositoryList ->
            // Update the cached copy of the words in the adapter.
            progressBar.visibility = View.INVISIBLE
            displayRepoList(repositoryList)
        })
    }

    private fun displayRepoList(repositoryList: ArrayList<UserRepoModel.Repository>) {

        mRepoListAdapter = RepoListAdapter(repositoryList,this)
        mLayoutManager = LinearLayoutManager(this)
        repositoryRecyclerView.setLayoutManager(mLayoutManager)
        repositoryRecyclerView.setItemAnimator(DefaultItemAnimator())
        repositoryRecyclerView.setAdapter(mRepoListAdapter)
    }

    override fun onRepoClicked(repoModel: UserRepoModel.Repository) {
        val intent = Intent(applicationContext, RepositoryWebPageActivity::class.java)
        intent.putExtra("RepoUrl",repoModel.html_url)
        startActivity(intent)
    }
}