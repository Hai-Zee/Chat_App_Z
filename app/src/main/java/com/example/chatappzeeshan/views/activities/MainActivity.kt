package com.example.chatappzeeshan.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.chatappzeeshan.R
import com.example.chatappzeeshan.daos.ChatDao
import com.example.chatappzeeshan.daos.UserDao
import com.example.chatappzeeshan.databinding.ActivityMainBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(activityMainBinding.root)

    }

    override fun onStart() {
        super.onStart()

        if (Firebase.auth.currentUser != null) {
            userDao = UserDao()

            userDao.userCollection
                .document(userDao.getCurrentUser().userId)
                .update("status", true)
        }
    }

    override fun onStop() {
        super.onStop()
        if (Firebase.auth.currentUser != null) {
            userDao = UserDao()
            userDao.userCollection
                .document(userDao.getCurrentUser().userId)
                .update("status", false)
        }
    }
}