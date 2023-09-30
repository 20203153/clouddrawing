package kr.ac.kookmin.clouddrawing.dto

import android.util.Log
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

data class User(
    val name: String = "",
    val email: String = "",
    val photoURL: String = "",
    val uid: String = ""
) {
    companion object {
        /* fun getUserById(uid: String): User {

        } */

        fun getCurrentUser(): User? {
            val it = Firebase.auth.currentUser
            return if (it != null) {
                User(it.displayName!!, it.email!!, it.photoUrl.toString(), it.uid)
            } else null
        }
    }

    fun updateProfile(request: UserProfileChangeRequest) {
        val user = Firebase.auth.currentUser

        user?.updateProfile(request)
            ?.addOnCompleteListener {
                if(it.isComplete)
                    Log.d("AUTH", "User Profile update done.")
            }
            ?.addOnFailureListener {
                Log.d("AUTH", "User Profile isan't updated.")
            }
    }
}