package com.example.chatappzeeshan.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.chatappzeeshan.R
import com.example.chatappzeeshan.daos.UserDao
import com.example.chatappzeeshan.databinding.FragmentMainViewBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainViewFragment : Fragment() {

    private lateinit var fragmentMainViewBinding: FragmentMainViewBinding
    private lateinit var chatFragment: ChatFragment
    private lateinit var usersFragment: UsersFragment
    private lateinit var profileFragment: ProfileFragment
    private lateinit var userDao: UserDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentMainViewBinding = FragmentMainViewBinding.inflate(inflater, container, false)
        return fragmentMainViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chatFragment = ChatFragment()
        usersFragment = UsersFragment()
        profileFragment = ProfileFragment()

        userDao = UserDao()

//        val viewPagerAdapter = ViewPagerAdapter(activity?.supportFragmentManager!!)
        val viewPagerAdapter = ViewPagerAdapter(childFragmentManager)

        viewPagerAdapter.addFragment(chatFragment, "CHATS")
        viewPagerAdapter.addFragment(usersFragment, "USERS")
        viewPagerAdapter.addFragment(profileFragment, "PROFILE")

        fragmentMainViewBinding.viewPager.adapter = viewPagerAdapter
        fragmentMainViewBinding.tabLayout.setupWithViewPager(fragmentMainViewBinding.viewPager)

//        TabLayoutMediator(
//            fragmentMainViewBinding.tabLayout,
//            fragmentMainViewBinding.viewPager2
//        ) { tab, position ->
//            tab.text = titleList[position]
//        }.attach()
    }


    class ViewPagerAdapter(fm: FragmentManager) :
        FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        private val fragmentList = ArrayList<Fragment>()
        private val titleList = ArrayList<String>()

        override fun getCount(): Int {
            return fragmentList.size
        }

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleList[position]
        }

        fun addFragment(fragment: Fragment, title: String) {
            fragmentList.add(fragment)
            titleList.add(title)
        }
    }


    override fun onStart() {
        super.onStart()

//        val map = HashMap<String, Any>()
//        map["status"] = true
//        userDao.userCollection
//            .document(userDao.getCurrentUser().userId)
//            .update(map)
        userDao.userCollection
            .document(userDao.getCurrentUser().userId)
            .update("status", true)
    }


    override fun onResume() {
        super.onResume()

//        val map = HashMap<String, Any>()
//        map["status"] = true
//        userDao.userCollection
//            .document(userDao.getCurrentUser().userId)
//            .update(map)
        userDao.userCollection
            .document(userDao.getCurrentUser().userId)
            .update("status", true)
    }

    override fun onPause() {
        super.onPause()

//        val map = HashMap<String, Any>()
//        map["status"] = false
//        userDao.userCollection
//            .document(userDao.getCurrentUser().userId)
//            .update(map)
        userDao.userCollection
            .document(userDao.getCurrentUser().userId)
            .update("status", false)
    }

    override fun onStop() {
        super.onStop()

//        val map = HashMap<String, Any>()
//        map["status"] = false
//        userDao.userCollection
//            .document(userDao.getCurrentUser().userId)
//            .update(map)
        userDao.userCollection
            .document(userDao.getCurrentUser().userId)
            .update("status", false)
    }

}