package com.example.chatappzeeshan.viewmodel

import android.service.autofill.UserData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatappzeeshan.modal.Chat
import com.example.chatappzeeshan.modal.UsersData
import com.example.chatappzeeshan.repository.Repo
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class MyViewModel : ViewModel() {
    private val repo = Repo()
    private var user = UsersData()

    fun getUsers(): MutableLiveData<FirestoreRecyclerOptions<UsersData>> {
        return repo.getUsers()
    }

    fun setUser(user:UsersData) {
        this.user = user
    }

    fun getUser() : UsersData{
        return user
    }

    fun getChats() : MutableLiveData<FirestoreRecyclerOptions<Chat>>{
        return repo.getChats()
    }

    fun getChatList() : MutableLiveData<FirestoreRecyclerOptions<UsersData>>{
        return repo.getChatList()
    }
}