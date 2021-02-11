package com.example.chatappzeeshan.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatappzeeshan.databinding.UsersCustomViewBinding
import com.example.chatappzeeshan.modal.UsersData
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UsersAdapter(options: FirestoreRecyclerOptions<UsersData>, val usersItemListener: UsersItemListener)
    : FirestoreRecyclerAdapter<UsersData, UsersAdapter.ViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val usersCustomViewBinding = UsersCustomViewBinding.inflate(layoutInflater, parent, false)
        usersCustomViewBinding.usersItemListener = usersItemListener
        return ViewHolder(usersCustomViewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: UsersData) {

        if ( Firebase.auth.currentUser?.uid != model.userId){
            holder.usersCustomViewBinding.user = model
        } else {
            holder.usersCustomViewBinding.root.visibility = View.GONE
            holder.usersCustomViewBinding.root.layoutParams.height = 0
            return
        }
    }

    class ViewHolder(val usersCustomViewBinding: UsersCustomViewBinding)
        : RecyclerView.ViewHolder(usersCustomViewBinding.root)

    interface UsersItemListener{
        fun onUserClicked(user : UsersData)
    }
}