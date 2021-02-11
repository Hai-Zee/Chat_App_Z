package com.example.chatappzeeshan.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatappzeeshan.R
import com.example.chatappzeeshan.adapter.UsersAdapter
import com.example.chatappzeeshan.databinding.FragmentUsersBinding
import com.example.chatappzeeshan.modal.UsersData
import com.example.chatappzeeshan.viewmodel.MyViewModel

class UsersFragment : Fragment(), UsersAdapter.UsersItemListener {

    private lateinit var myViewModel: MyViewModel
    private lateinit var fragmentUsersBinding: FragmentUsersBinding
    private lateinit var usersAdapter: UsersAdapter
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentUsersBinding = FragmentUsersBinding.inflate(inflater, container, false)
        return fragmentUsersBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        myViewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
        fragmentUsersBinding.usersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        fragmentUsersBinding.usersRecyclerView.setHasFixedSize(true)
    }

    override fun onStart() {
        super.onStart()
        myViewModel.getUsers().observe(viewLifecycleOwner, Observer {

            usersAdapter = UsersAdapter(it, this)
            usersAdapter.startListening()
            fragmentUsersBinding.usersRecyclerView.adapter = usersAdapter
            // hide progress bar & show recyclerview
            fragmentUsersBinding.usersRecyclerView.visibility = View.VISIBLE
            fragmentUsersBinding.usersProgressBar.visibility = View.INVISIBLE
        })
    }

    override fun onStop() {
        super.onStop()
        usersAdapter.stopListening()
    }

    override fun onUserClicked(user: UsersData) {
        myViewModel.setUser(user)
        navController.navigate(R.id.action_mainViewFragment_to_messegeFragment)
    }
}