package com.example.gituserapp.features.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gituserapp.R
import com.example.gituserapp.features.details.UserDetailsActivity
import com.example.gituserapp.model.UsersModel
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class SearchActivity : AppCompatActivity(), UserListAdapter.Listener {

    //UI declarations
    lateinit var userRecyclerView: RecyclerView
    lateinit var searchText: TextView
    lateinit var sortButton: Button
    lateinit var mListAdapter: UserListAdapter
    lateinit var mLayoutManager : LinearLayoutManager
    lateinit var progressBar:ProgressBar

    //local declarations
    var isLastPage: Boolean = false
    var isLoading: Boolean = false

    var searchedString : String = ""
    var totalPages : Int = 1
    var currentPage : Int = 1

    //other declarations
    private val subscriptions = CompositeDisposable()
    private lateinit var searchViewModel: SearchViewModel
    private var usersList: ArrayList<UsersModel.Items> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


        userRecyclerView = findViewById(R.id.users_recyclerView)
        searchText = findViewById(R.id.text_search)
        sortButton = findViewById(R.id.sort_button)
        progressBar = findViewById(R.id.progressBar)

        searchViewModel = ViewModelProviders.of(this)[SearchViewModel::class.java] // ViewModel and livedata is used.

        sortButton.setOnClickListener {
            showSortDialog()
        }
    }


    // Search menu in actionbar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        //on typing the keywords in the search view of toolbar the keywords are passed to the api call
        // and the result is brought back to the main thread


        subscriptions.addAll(Observable.create(ObservableOnSubscribe<String> { subscriber ->
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    subscriber.onNext(newText!!)
                    return false
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    subscriber.onNext(query!!)
                    return false
                }
            })
        })
            .map { text -> text.toLowerCase().trim() }
            .debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .filter { text -> text.isNotBlank() }
            .switchMapSingle {
                    result ->

                                searchedString = result
                searchViewModel.getUserData(searchedString,currentPage)  //making network call to the server

            }
            .subscribe { usersData ->

                //actual results of the search are fetched here
                displayUsersData(usersData)
            })
        return true
    }


    //display User data to recyclerview
    fun displayUsersData(usersData: UsersModel.Result){

        sortButton.visibility= View.VISIBLE
        searchText.visibility = View.GONE
        progressBar.visibility = View.INVISIBLE

        totalPages = usersData.total_count/30  // counting the number of pages

        usersList = ArrayList(usersData.items)
        mListAdapter = UserListAdapter(usersList,this)
        mLayoutManager = GridLayoutManager(this,2)
        userRecyclerView.setLayoutManager(mLayoutManager)
        userRecyclerView.setItemAnimator(DefaultItemAnimator())
        userRecyclerView.setAdapter(mListAdapter)

        userRecyclerView.addOnScrollListener(object : PaginationScrollListener(mLayoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                progressBar.visibility = View.VISIBLE

                getMoreUsers()
            }
        })

    }

    //fetch more users on scroll functionality
    fun getMoreUsers() {
        //after fetching data indicator is set back to false
        isLoading = false

        //checking if current page is less than the totaal number of pages and then fetching data
        if(currentPage <= totalPages){

            currentPage++

            searchViewModel.getSubsequentUserData(searchedString,currentPage).observe(this, Observer<ArrayList<UsersModel.Items>> {
                    subsequentUserList ->
                progressBar.visibility = View.INVISIBLE
                usersList.addAll(subsequentUserList)
                mListAdapter.notifyDataSetChanged()
            })

        }else{
            progressBar.visibility = View.INVISIBLE
        }

    }

    fun showSortDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose Sort Option")

        val sortArray = arrayOf("Name(A-Z)", "Name(Z-A)", "Rank Ascending", "Rank Descending")
        builder.setItems(sortArray) { dialog, which ->
            when (which) {
                0 -> {
                    searchViewModel.sortNameAsc(usersList).observe(this, Observer<ArrayList<UsersModel.Items>> {
                            sortedUsersList ->
                        mListAdapter.updateSortedData(sortedUsersList)
                        mLayoutManager.scrollToPositionWithOffset(0, 0)
                    })
                }
                1 -> {
                    searchViewModel.sortNameDesc(usersList).observe(this, Observer<ArrayList<UsersModel.Items>> {
                            sortedUsersList ->
                        mListAdapter.updateSortedData(sortedUsersList)
                        mLayoutManager.scrollToPositionWithOffset(0, 0)
                    })
                }
                2 -> {
                    searchViewModel.sortRankAsc(usersList).observe(this, Observer<ArrayList<UsersModel.Items>> {
                            sortedUsersList ->
                        mListAdapter.updateSortedData(sortedUsersList)
                        mLayoutManager.scrollToPositionWithOffset(0, 0)
                    })
                }
                3 -> {
                    searchViewModel.sortRankDesc(usersList).observe(this, Observer<ArrayList<UsersModel.Items>> {
                            sortedUsersList ->
                        mListAdapter.updateSortedData(sortedUsersList)
                        mLayoutManager.scrollToPositionWithOffset(0, 0)
                    })
                }
            }
        }
        val dialog = builder.create()
        dialog.show()
    }

    override fun onUserClicked(userModel: UsersModel.Items) {
        val intent = Intent(applicationContext, UserDetailsActivity::class.java)
        intent.putExtra("loginId",userModel.login)
        intent.putExtra("profileUrl",userModel.avatar_url)
        startActivity(intent)
    }

}

