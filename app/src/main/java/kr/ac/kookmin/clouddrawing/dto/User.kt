package kr.ac.kookmin.clouddrawing.dto

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

data class User(
    val name: String = "",
    val email: String = "",
    val photoURL: String = "",
    val uid: String? = ""
) {
    companion object {
        private val user = FirebaseFirestore.getInstance().collection("user")
        fun getUserById(uid: String): User? {
            var result: User? = null
            user.document(uid).get()
                .addOnSuccessListener { result = it.toObject<User>() }

            return result
        }

        fun getCurrentUser(): User? {
            var result: User? = null
            val it = Firebase.auth.currentUser

            if (it != null) {
                user.document(it.uid).get()
                    .addOnSuccessListener { result = it.toObject<User>() }
            }

            return result
        }

        fun createCurrentUser(): Boolean {
            var result = false
            val it = Firebase.auth.currentUser

            user.document(it!!.uid).set(
                User(
                    it.displayName!!,
                    it.email!!,
                    it.photoUrl.toString(),
                    it.uid
                )
            ).addOnSuccessListener { result = true }

            return result
        }
    }

    fun updateProfile(updateUser: User): Boolean {
        var result = true

        val user = user.document(this.uid!!)

        val update = hashMapOf<String, Any>(
            "name" to updateUser.name,
            "email" to updateUser.email,
            "photoURL" to updateUser.photoURL
        )
        update.filter { it.value != "" }
        user.update(update)
            .addOnSuccessListener { result = true }

        return result
    }
}