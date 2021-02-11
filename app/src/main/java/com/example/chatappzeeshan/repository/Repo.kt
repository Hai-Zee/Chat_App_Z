package com.example.chatappzeeshan.repository

import androidx.lifecycle.MutableLiveData
import com.example.chatappzeeshan.daos.UserDao
import com.example.chatappzeeshan.modal.Chat
import com.example.chatappzeeshan.modal.UsersData
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class Repo {
    private val dao = UserDao()
    private val chatCollection = dao.firebaseFirestore.collection("Chats")
    private val chatListCollection = dao.firebaseFirestore.collection("ChatList")

    private val mutableUserLiveData = MutableLiveData<FirestoreRecyclerOptions<UsersData>>()
    private val mutableChatLiveData = MutableLiveData<FirestoreRecyclerOptions<Chat>>()
    private val mutableChatListLiveData = MutableLiveData<FirestoreRecyclerOptions<UsersData>>()

    fun getUsers(): MutableLiveData<FirestoreRecyclerOptions<UsersData>> {
        CoroutineScope(Dispatchers.IO).launch() {
            val usersQuery = dao.userCollection
                .get().await().query

            val userOption: FirestoreRecyclerOptions<UsersData> =
                FirestoreRecyclerOptions.Builder<UsersData>()
                    .setQuery(usersQuery, UsersData::class.java)
                    .build()

            withContext(Dispatchers.Main) {
                mutableUserLiveData.value = userOption
            }
        }
        return mutableUserLiveData
    }

    fun getChats(): MutableLiveData<FirestoreRecyclerOptions<Chat>> {
        CoroutineScope(Dispatchers.IO).launch {
            val chatQuery = chatCollection
                .orderBy("timeStamp", Query.Direction.ASCENDING)

            val chatOption: FirestoreRecyclerOptions<Chat> =
                FirestoreRecyclerOptions.Builder<Chat>()
                    .setQuery(chatQuery, Chat::class.java)
                    .build()

            withContext(Dispatchers.Main) {
                mutableChatLiveData.value = chatOption
            }
        }
        return mutableChatLiveData
    }

    fun getChatList(): MutableLiveData<FirestoreRecyclerOptions<UsersData>> {
        CoroutineScope(Dispatchers.IO).launch {
            val chatListQuery = chatListCollection
                .document(dao.getCurrentUser().userId)
                .collection("MyFriends")
                .get().await().query

            val chatListOption : FirestoreRecyclerOptions<UsersData> =
                FirestoreRecyclerOptions.Builder<UsersData>()
                    .setQuery(chatListQuery, UsersData::class.java)
                    .build()

            withContext(Dispatchers.Main){
                mutableChatListLiveData.value = chatListOption
            }
        }
        return mutableChatListLiveData
    }
}