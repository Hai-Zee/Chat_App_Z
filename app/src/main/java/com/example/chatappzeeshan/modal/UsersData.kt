package com.example.chatappzeeshan.modal

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

class UsersData(
    val userName: String = "",
    val userId: String = "",
    val userImageUrl: String = "",
    var status: Boolean = false
) {

    companion object {
        @JvmStatic
        @BindingAdapter("android:setImageFromUrl")
        fun loadImage(imageView: ImageView, url: String?) {

            url?.let {
                Glide.with(imageView.context)
                    .load(url)
                    .circleCrop()
                    .into(imageView)
            }
        }

        @BindingAdapter("android:setStatus")
        @JvmStatic
        fun checkStatus(imageView: ImageView, status: Boolean) {
            if(status)
                imageView.visibility = View.VISIBLE
            else
                imageView.visibility = View.GONE
        }
    }
}