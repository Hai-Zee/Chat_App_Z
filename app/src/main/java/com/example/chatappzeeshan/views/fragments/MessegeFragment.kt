package com.example.chatappzeeshan.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatappzeeshan.R
import com.example.chatappzeeshan.adapter.MessageAdapter
import com.example.chatappzeeshan.daos.ChatDao
import com.example.chatappzeeshan.daos.UserDao
import com.example.chatappzeeshan.databinding.FragmentMessegeBinding
import com.example.chatappzeeshan.modal.UsersData
import com.example.chatappzeeshan.viewmodel.MyViewModel

class MessegeFragment : Fragment() {

    private lateinit var fragmentMessegeBinding: FragmentMessegeBinding
    private lateinit var myViewModel: MyViewModel
    private lateinit var navController: NavController
    private lateinit var messageAdapter: MessageAdapter
    private var userDao: UserDao = UserDao()
    private var chatDao: ChatDao = ChatDao()
    private lateinit var currentUser: UsersData
    private lateinit var receiverUser: UsersData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentMessegeBinding = FragmentMessegeBinding.inflate(inflater, container, false)
        return fragmentMessegeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        navController = Navigation.findNavController(view)
        navController = Navigation.findNavController(requireActivity(), R.id.fragment_host)
        // Initialising  viewModel & users
        myViewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
        receiverUser = myViewModel.getUser()
        currentUser = userDao.getCurrentUser()
        // sending data to layout file
        fragmentMessegeBinding.usersData = receiverUser
        // setting up recyclerview
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.stackFromEnd = true
        fragmentMessegeBinding.messageRecyclerView.layoutManager = linearLayoutManager
        fragmentMessegeBinding.messageRecyclerView.setHasFixedSize(true)

        clickListeners()
    }

    private fun clickListeners() {
//        Back button inside the Chatting Page
        fragmentMessegeBinding.backButtonFromMessage.setOnClickListener {
            navController.popBackStack()
        }

//        Send Button to send the message
        fragmentMessegeBinding.messageSendBtn.setOnClickListener {

            val message = fragmentMessegeBinding.typeMessage.text.toString().trim()

            if (message.isNotEmpty()) {
                fragmentMessegeBinding.typeMessage.error = null
                fragmentMessegeBinding.typeMessage.text.clear()
                // Sending Message
                chatDao.sendMessage(currentUser, receiverUser, message, System.currentTimeMillis().toString())
            } else {
                fragmentMessegeBinding.typeMessage.error = "Required"
            }
        }
    }


    override fun onStart() {
        super.onStart()

//        userDao.userCollection
//            .document(userDao.getCurrentUser().userId)
//            .update("status", true)
//
//        fragmentMessegeBinding.usersData = receiverUser

        //  Getting chats by observing mutableChatLiveData
        myViewModel.getChats().observe(viewLifecycleOwner, Observer {
            messageAdapter = MessageAdapter(it, receiverUser)
            messageAdapter.startListening()
            fragmentMessegeBinding.messageRecyclerView.adapter = messageAdapter

        })
    }


//    override fun onResume() {
//        super.onResume()
//
//        userDao.userCollection
//            .document(userDao.getCurrentUser().userId)
//            .update("status", true)
//
//        fragmentMessegeBinding.usersData = receiverUser
//    }



    override fun onStop() {
        super.onStop()
        messageAdapter.stopListening()
    }

}