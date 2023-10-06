package kr.ac.kookmin.clouddrawing.dto

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

data class User(
    val name: String = "",
    val email: String = "",
    val photoURL: String = "",
    val uid: String? = ""
) {
    companion object {
        private val user = FirebaseFirestore.getInstance().collection("user")
        suspend fun getUserById(uid: String): User? {
            var result: User?
            val query = user.document(uid).get().await()

            result = query.toObject<User>()

            return result
        }

        suspend fun getCurrentUser(): User? {
            var result: User? = null
            val it = Firebase.auth.currentUser

            if (it != null) {
                val user = user.document(it.uid).get().await()
                result = user.toObject<User>()
            }

            return result
        }

        suspend fun createCurrentUser(): Boolean {
            val it = Firebase.auth.currentUser

            val query = user.document(it!!.uid).set(
                User(
                    it.displayName!!,
                    it.email!!,
                    it.photoUrl.toString(),
                    it.uid
                )
            ).await()

            return true
        }
    }

    suspend fun updateProfile(updateUser: User): Boolean {
        val user = user.document(this.uid!!)

        val update = hashMapOf<String, Any>(
            "name" to updateUser.name,
            "email" to updateUser.email,
            "photoURL" to updateUser.photoURL
        )
        update.filter { it.value != "" }
        user.update(update).await()

        return true
    }

    suspend fun deleteUser(): Boolean {
        user.document(this.uid!!)
            .delete().await()
        return true
    }
}