package com.example.jareader3.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import androidx.lifecycle.viewModelScope
import com.example.jareader3.model.MUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class LoginScreenViewModel:ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun signwithemailandpassword(email: String, password: String, home: () -> Unit) =
        viewModelScope.launch {
            try {

                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("FB", "succeess.${task.result.toString()}")
                            //take them home"
                            home()
                        } else {
                            Log.d("FB", "not succeess.${task.result.toString()}")
                        }
                    }
            } catch (ex: Exception) {
                Log.d("FB", "Sign with email and password${ex.message}")
            }
        }

    fun createuserwithemailamdpassword(
        email: String,
        password: String,
        home: () -> Unit
    ) {
        if (_loading.value == false) {
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user_name= task.result.user?.email?.split("@")?.get(0)
                        if (user_name != null) {
                            createUser(user_name)
                        }
                        home()
                    } else {
                        Log.d("FB", "User not created. ${task.result.toString()}")
                    }
                    _loading.value = false

                }
        }
    }

    private fun createUser(displayName:String){
        val userId=auth.currentUser?.uid
        val user= MUser(userId=userId.toString(),
            displayName=displayName,
        avatarUrl = "",
        quote = "Life is great",
            profession = "Android developer",
            id=null
        ).toMap()
        FirebaseFirestore.getInstance().collection("users").add(user)


    }

}

