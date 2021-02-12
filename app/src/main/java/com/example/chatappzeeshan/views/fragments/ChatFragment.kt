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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.chatappzeeshan.R
import com.example.chatappzeeshan.adapter.UsersAdapter
import com.example.chatappzeeshan.databinding.FragmentChatBinding
import com.example.chatappzeeshan.modal.UsersData
import com.example.chatappzeeshan.viewmodel.MyViewModel

class ChatFragment : Fragment(), UsersAdapter.UsersItemListener {

    private lateinit var fragmentChatBinding: FragmentChatBinding
    private lateinit var usersAdapter: UsersAdapter
    private lateinit var myViewModel: MyViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        fragmentChatBinding = FragmentChatBinding.inflate(inflater, container, false)
        return fragmentChatBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        myViewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)

        fragmentChatBinding.chatFragmentRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        fragmentChatBinding.chatFragmentRecyclerview.setHasFixedSize(true)

//        fragmentChatBinding.swipeRefreshLayout.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener{
//            override fun onRefresh() {
//                usersAdapter.notifyDataSetChanged()
//                fragmentChatBinding.swipeRefreshLayout.isRefreshing = false
//            }
//        })

    }

    override fun onStart() {
        super.onStart()
        myViewModel.getChatList().observe(viewLifecycleOwner, Observer {
            usersAdapter = UsersAdapter(it, this)
            usersAdapter.startListening()
            fragmentChatBinding.chatFragmentRecyclerview.adapter = usersAdapter
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