package com.example.chatappzeeshan.daos

import com.example.chatappzeeshan.modal.Chat
import com.example.chatappzeeshan.modal.UsersData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatDao {
     val firebaseFirestore = FirebaseFirestore.getInstance()
     val chatCollection = firebaseFirestore.collection("Chats")
     val chatListCollection = firebaseFirestore.collection("ChatList")

    private val userDao = UserDao()

    fun sendMessage(
        currentUser: UsersData,
        receiverUser: UsersData,
        message: String,
        currentTimeMillis: String
    ) {

        val chat = Chat(currentUser, receiverUser, message, currentTimeMillis)
        CoroutineScope(Dispatchers.IO).launch {
            chatCollection.document().set(chat)

            // setting friends status to offline, because i don't want status functionality in chats fragments
            receiverUser.status = false

            chatListCollection.document(userDao.getCurrentUser().userId)
                .collection("MyFriends")
                .document(receiverUser.userId)
                .set(receiverUser)
        }
    }
}