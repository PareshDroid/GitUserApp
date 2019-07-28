package com.example.gituserapp.features.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gituserapp.R
import com.example.gituserapp.features.search.UserListAdapter
import com.example.gituserapp.model.UserRepoModel
import com.example.gituserapp.model.UsersModel
import kotlinx.android.synthetic.main.adapter_user_details.view.*

class RepoListAdapter(private val userArray : ArrayList<UserRepoModel.Repository>, private val listener : Listener) : RecyclerView.Adapter<RepoListAdapter.ViewHolder>() {


    interface Listener {
        fun onRepoClicked(repoModel : UserRepoModel.Repository)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(userArray[position], listener, position)
    }

    override fun getItemCount(): Int = userArray.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_user_details, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        fun bind(userRepoModel: UserRepoModel.Repository, listener: RepoListAdapter.Listener, position: Int) {

            itemView.setOnClickListener{ listener.onRepoClicked(userRepoModel) }
            itemView.text_description.text = userRepoModel.description
            itemView.text_fork.text = "Fork Count: "+ userRepoModel.forks
            itemView.text_language.text = "Language:" + userRepoModel.language
        }
    }

}