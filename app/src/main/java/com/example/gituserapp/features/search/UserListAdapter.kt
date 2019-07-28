package com.example.gituserapp.features.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gituserapp.R
import com.example.gituserapp.model.UsersModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_user_layout.view.*

class UserListAdapter(private val userArray : ArrayList<UsersModel.Items>, private val listener : Listener) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    interface Listener {
        fun onUserClicked(userModel : UsersModel.Items)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(userArray[position], listener,position)
    }

    override fun getItemCount(): Int = userArray.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_user_layout, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        fun bind(usersModel: UsersModel.Items, listener: Listener, position: Int) {

            itemView.setOnClickListener{ listener.onUserClicked(usersModel) }
            itemView.name.text = "Name:"+ usersModel.login+ "\nScore" + usersModel.score
            Picasso.get().load(usersModel.avatar_url).fit().into(itemView.user_image)
        }
    }

    fun updateData(listItems: ArrayList<UsersModel.Items>) {
        notifyDataSetChanged()
    }

    fun updateSortedData(listItems: ArrayList<UsersModel.Items>) {
        this.userArray.clear()
        this.userArray.addAll(listItems)
        notifyDataSetChanged()
    }
}