package com.example.chatappzeeshan.daos

import com.example.chatappzeeshan.modal.UsersData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserDao {
    val firebaseFirestore = FirebaseFirestore.getInstance()
    val userCollection = firebaseFirestore.collection("Users")
    val firebaseUser = Firebase.auth.currentUser!!
    private var currentUser = UsersData(firebaseUser.displayName!!, firebaseUser.uid, firebaseUser.photoUrl.toString())

    fun addUsers(user: UsersData) {
        CoroutineScope(Dispatchers.IO).launch {
            userCollection
                .document(user.userId)
                .set(user)
        }
    }

    fun getCurrentUser(): UsersData {
        return currentUser
    }
}
