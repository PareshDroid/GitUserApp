package com.example.gituserapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.adapter_user_layout.view.*

class UserListAdapter(private val newsList : List<UsersModel.Items>) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(newsList[position], position)
    }

    override fun getItemCount(): Int = newsList.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_user_layout, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        fun bind(usersModel: UsersModel.Items, position: Int) {

            itemView.name.text = usersModel.login

        }
    }
}