package com.example.chatappzeeshan.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.chatappzeeshan.R
import com.example.chatappzeeshan.daos.UserDao
import com.example.chatappzeeshan.databinding.FragmentProfileBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    private lateinit var fragmentProfileBinding : FragmentProfileBinding
    private lateinit var navController: NavController
    private val userDao = UserDao()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false)
        return fragmentProfileBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        fragmentProfileBinding.userProfile = userDao.getCurrentUser()

        fragmentProfileBinding.signOutButton.setOnClickListener {

            // setting status to offline i.e. false
            userDao.userCollection
                .document(userDao.getCurrentUser().userId)
                .update("status", false)

            Firebase.auth.signOut()
            navController.navigate(R.id.action_mainViewFragment_to_startFragment)
        }
    }
}