package com.example.chatappzeeshan.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatappzeeshan.R
import com.example.chatappzeeshan.daos.UserDao
import com.example.chatappzeeshan.modal.Chat
import com.example.chatappzeeshan.modal.UsersData
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class MessageAdapter(
    options: FirestoreRecyclerOptions<Chat>,
    val receiverUser: UsersData
) :
    FirestoreRecyclerAdapter<Chat, MessageAdapter.ViewHolder>(options) {

    private val MSG_TYPE_RIGHT = 0
    private val MSG_TYPE_LEFT = 1

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return if (viewType == MSG_TYPE_RIGHT) {
//            val inflater = LayoutInflater.from(parent.context)
//            val messageItemRightBinding = MessageItemRightBinding.inflate(inflater, parent, false)
//            ViewHolder(messageItemRightBinding = messageItemRightBinding)
//        } else {
//            val inflater = LayoutInflater.from(parent.context)
//            val messageItemLeftBinding = MessageItemLeftBinding.inflate(inflater, parent, false)
//            ViewHolder(messageItemLeftBinding = messageItemLeftBinding)
//        }
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == MSG_TYPE_RIGHT) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.message_item_right, parent, false)
            ViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.message_item_left, parent, false)
            ViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Chat) {

        val dao = UserDao()
        val currentUser = dao.getCurrentUser()

        Log.d("check1", "onBindViewHolder: ${currentUser.userName}")
        holder.msgTextView.text = model.message

//        if (model.sender?.userId.equals(currentUser.userId) && model.receiver?.userId.equals(receiverUser.userId) ||
//            model.receiver?.userId.equals(currentUser.userId) && model.sender?.userId.equals(receiverUser.userId)
//        ) {
//
//            if (holder.itemViewType == MSG_TYPE_RIGHT)
//                holder.messageItemRightBinding?.message = model
//            else holder.messageItemLeftBinding?.message = model
//        }
    }

//    class ViewHolder(
//        val messageItemRightBinding: MessageItemRightBinding? = null,
//        val messageItemLeftBinding: MessageItemLeftBinding? = null
//    ) : RecyclerView.ViewHolder(
//        if (messageItemRightBinding != null) messageItemRightBinding.root else messageItemLeftBinding?.root!!
//    )

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val msgTextView = itemView.findViewById<TextView>(R.id.msg_text_view)
    }

    override fun getItemViewType(position: Int): Int {
        val userDao = UserDao()
        Log.d("check", "getItemViewType: ${userDao.getCurrentUser().userName}")
        return if (userDao.getCurrentUser().userId.equals(getItem(position).sender?.userId)) MSG_TYPE_RIGHT else MSG_TYPE_LEFT
    }
}























//
//
//
//
//
//
//
//package com.example.chatappzeeshan.adapter
//
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.example.chatappzeeshan.R
//import com.example.chatappzeeshan.daos.UserDao
//import com.example.chatappzeeshan.databinding.*
//import com.example.chatappzeeshan.modal.Chat
//import com.example.chatappzeeshan.modal.UsersData
//import com.firebase.ui.firestore.FirestoreRecyclerAdapter
//import com.firebase.ui.firestore.FirestoreRecyclerOptions
//
//class MessageAdapter(
//    options: FirestoreRecyclerOptions<Chat>,
//    val receiverUser: UsersData
//) :
//    FirestoreRecyclerAdapter<Chat, MessageAdapter.ViewHolder>(options) {
//
//    private val MSG_TYPE_RIGHT = 0
//    private val MSG_TYPE_LEFT = 1
//    private val userDao = UserDao()
//
////    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
////        return if (viewType == MSG_TYPE_RIGHT) {
////            val inflater = LayoutInflater.from(parent.context)
////            val messageItemRightBinding = MessageItemRightBinding.inflate(inflater, parent, false)
////            ViewHolder(messageItemRightBinding = messageItemRightBinding)
////        } else {
////            val inflater = LayoutInflater.from(parent.context)
////            val messageItemLeftBinding = MessageItemLeftBinding.inflate(inflater, parent, false)
////            ViewHolder(messageItemLeftBinding = messageItemLeftBinding)
////        }
////    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return if (viewType == MSG_TYPE_RIGHT){
//            val view = LayoutInflater.from(parent.context).inflate(R.layout.message_item_right, parent, false)
//            ViewHolder(view)
//        } else {
//            val view = LayoutInflater.from(parent.context).inflate(R.layout.message_item_left, parent, false)
//            ViewHolder(view)
//        }
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Chat) {
//
//        val dao = UserDao()
//        val currentUser = dao.getCurrentUser()
//
//        if (model.sender?.userId.equals(currentUser.userId) && model.receiver?.userId.equals(receiverUser.userId) ||
//            model.receiver?.userId.equals(currentUser.userId) && model.sender?.userId.equals(receiverUser.userId)
//        ) {
//            if (holder.itemViewType == MSG_TYPE_RIGHT) {
//
//            } else {
//
//            }
//        }
//
////        if (model.sender?.userId.equals(currentUser.userId) && model.receiver?.userId.equals(receiverUser.userId) ||
////            model.receiver?.userId.equals(currentUser.userId) && model.sender?.userId.equals(receiverUser.userId)
////        ) {
////
////            if (holder.itemViewType == MSG_TYPE_RIGHT)
////                holder.messageItemRightBinding?.message = model
////            else holder.messageItemLeftBinding?.message = model
////        }
//    }
//
////    class ViewHolder(
////        val messageItemRightBinding: MessageItemRightBinding? = null,
////        val messageItemLeftBinding: MessageItemLeftBinding? = null
////    ) : RecyclerView.ViewHolder(
////        if (messageItemRightBinding != null) messageItemRightBinding.root else messageItemLeftBinding?.root!!
////    )
//
//    class ViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val msgTextView = itemView.findViewById<TextView>(R.id.msg_text_view)
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        Log.d("check", "getItemViewType: ${userDao.getCurrentUser().userName}")
//        return if (userDao.getCurrentUser().userId.equals(getItem(position).sender?.userId)) MSG_TYPE_RIGHT else MSG_TYPE_LEFT
//    }
