package kr.ac.kookmin.clouddrawing.dto

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

data class User(
    var name: String = "",
    var email: String = "",
    var photoURL: String = "",
    val uid: String? = "",
    var lastLogin: Timestamp? = Timestamp.now()
) {
    companion object {
        private val TAG = "UserDTO"

        private val user = FirebaseFirestore.getInstance().collection("user")
        suspend fun getUserById(uid: String): User? {
            val result: User?
            val query = user.document(uid).get().await()

            result = query.toObject<User>()

            return result
        }

        suspend fun getCurrentUser(): User? {
            return try {
                val it = Firebase.auth.currentUser

                if (it != null) {
                    val user = user.document(it.uid).get().await()
                    Log.d(TAG, "User currentUser get success")

                    return user.toObject<User>()
                } else {
                    Log.e(TAG, "User currentGet failed. User is not login.")
                    null
                }
            } catch (e: Exception) {
                Log.e(TAG, "User currentGet failed. ${e.localizedMessage}")
                return null
            }
        }

        suspend fun createCurrentUser(): Boolean {
            val it = Firebase.auth.currentUser

            val query = user.document(it!!.uid).set(
                User(
                    it.displayName!!,
                    it.email!!,
                    it.photoUrl.toString(),
                    it.uid,
                    Timestamp.now()
                )
            ).await()

            return true
        }
    }

    suspend fun updateProfile(updateUser: User): Boolean {
        return try {
            val user = user.document(this.uid!!)

            val update = hashMapOf<String, Any?>(
                "name" to updateUser.name,
                "email" to updateUser.email,
                "photoURL" to updateUser.photoURL,
                "lastLogin" to updateUser.lastLogin
            )
            update.filter { it.value != null }
            user.update(update).await()

            Log.d(TAG, "User update success")
            true
        } catch (e: Exception) {
            Log.e(TAG, "update fail. ${e.localizedMessage}")
            false
        }
    }

    suspend fun updateLastLogin(): Boolean {
        return try {
            val user = user.document(this.uid!!)
            user.update("lastLogin", Timestamp.now())
            .await()

            Log.d(TAG, "lastLogin update success")
            true
        } catch (e: Exception) {
            Log.e(TAG, "update fail. ${e.localizedMessage}")
            false
        }
    }

    suspend fun deleteUser(): Boolean {
        user.document(this.uid!!)
            .delete().await()
        return true
    }
}