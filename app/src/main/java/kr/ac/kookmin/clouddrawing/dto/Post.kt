package kr.ac.kookmin.clouddrawing.dto

import android.net.Uri
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

data class Post(
    var id: String? = "",
    var uid: String? = "",
    var title: String? = "",
    var lat: Double? = 0.0,
    var lng: Double? = 0.0,
    var addressAlias: String? = "",
    var friends: String? = "",
    var comment: String? = "",
    var image: MutableList<Uri> = mutableListOf(),
    var writeTime: Timestamp? = Timestamp.now(),
    var postTime: Timestamp? = Timestamp.now()
) {
    companion object {
        private val post = FirebaseFirestore.getInstance().collection("post")
        suspend fun getPostById(id: String): Post? {
            return post.document(id).get().await().toObject()
        }

        suspend fun getPostByUID(uid: String, limit: Long = 10L): List<Post> {
            val result = mutableListOf<Post?>()

            val posts = post.whereEqualTo("uid", uid)
                .orderBy("writeTime", Query.Direction.DESCENDING)
                .limit(limit)
                .get().await()

            posts.forEach { result.add(it.toObject()) }
            return result.filterNotNull()
        }

        suspend fun addPost(newPost: Post): String {
            val id = post.document().id

            newPost.id = id

            post.document(id).set(newPost).await()
            return id
        }
    }

    suspend fun addImage(images: List<ImageInfo>): Boolean {
        this.image + images

        post.document(this.id!!)
            .update("image", this.image).await()
        return true
    }

    suspend fun removeImage(images: List<ImageInfo>): Boolean {
        images.forEach {
            if(it.userId == "") it.userId = uid!!
            if(it.postId == "") it.postId = id!!
        }

        post.document(this.id!!)
            .update("image", FieldValue.arrayRemove(images))
            .await()
        return true
    }

    suspend fun update(updatePost: Post): Boolean {
        val post = post.document(this.id!!)

        val update = hashMapOf(
            "title" to updatePost.title,
            "lat" to updatePost.lat,
            "lng" to updatePost.lng,
            "addressAlias" to updatePost.addressAlias,
            "friends" to updatePost.friends,
            "comment" to updatePost.comment,
            "image" to updatePost.image
        )

        post.update(update).await()

        return true
    }

    suspend fun delete(): Boolean {
        post.document(this.id!!)
            .delete().await()
        return true
    }
}
