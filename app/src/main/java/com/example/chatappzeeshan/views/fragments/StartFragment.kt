package com.example.chatappzeeshan.views.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.chatappzeeshan.R
import com.example.chatappzeeshan.daos.UserDao
import com.example.chatappzeeshan.databinding.FragmentStartBinding
import com.example.chatappzeeshan.modal.UsersData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Api
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StartFragment : Fragment(), FirebaseAuth.AuthStateListener {

    val GOOGLE_SIGNIN_REQUEST_CODE = 101
    private lateinit var navController: NavController
    private lateinit var fragmentStartBinding: FragmentStartBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentStartBinding = FragmentStartBinding.inflate(inflater, container, false)
        return fragmentStartBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        if (Firebase.auth.currentUser != null)
            navController.navigate(R.id.action_startFragment_to_mainViewFragment)


        fragmentStartBinding.googleSignInButton.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // Getting googleSignInClient
        val client = GoogleSignIn.getClient(requireContext(), gso)
        // signing out client before loging in
        client.signOut()
        // getting signin intent
        val signInIntent = client.signInIntent

        startActivityForResult(signInIntent, GOOGLE_SIGNIN_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val task: Task<GoogleSignInAccount>? = GoogleSignIn.getSignedInAccountFromIntent(data)

        try {
            val account: GoogleSignInAccount? = task?.getResult(ApiException::class.java)
            handleFirebaseAuth(account?.idToken)
        } catch (e: ApiException) {
            Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun handleFirebaseAuth(idToken: String?) {
        val credential: AuthCredential = GoogleAuthProvider.getCredential(idToken, null)
        // showing progress bar
        fragmentStartBinding.signinProgressBar.visibility = View.VISIBLE
        //        kotlin style
        Firebase.auth.signInWithCredential(credential)
    }

    override fun onStart() {
        super.onStart()
        Firebase.auth.addAuthStateListener(this)
    }

    override fun onStop() {
        super.onStop()
        Firebase.auth.removeAuthStateListener(this)
    }

    override fun onAuthStateChanged(firebaseAuth: FirebaseAuth) {
        val user: FirebaseUser? = firebaseAuth.currentUser
        updateUI(user)
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val currentUser = UsersData(
                userName = user.displayName!!,
                userId = user.uid,
                userImageUrl = user.photoUrl.toString(),
                true
            )
            val dao = UserDao()
            dao.addUsers(currentUser)
            navController.navigate(R.id.action_startFragment_to_mainViewFragment)
        } else {
            fragmentStartBinding.signinProgressBar.visibility = View.INVISIBLE
        }
    }
}