package kr.ac.kookmin.clouddrawing.dto

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import java.util.Date

data class Post(
    var id: String? = "",
    var uid: String? = "",
    var lat: Float? = 0.0F,
    var lng: Float? = 0.0F,
    var addressAlias: String? = "",
    var comment: String? = "",
    var image: List<ImageInfo> = listOf(),
    var writeTime: Date?
) {
    companion object {
        private val post = FirebaseFirestore.getInstance().collection("post")
        suspend fun getPostById(id: String): Post? {
            var result: Post? = null
            result = post.document(id).get().await().toObject()
            return result
        }

        suspend fun getPostByUID(uid: String): List<Post> {
            val result = mutableListOf<Post?>()

            val posts = post.whereEqualTo("uid", uid).orderBy("writeTime")
                .get().await()

            posts.forEach { result.add(it.toObject()) }
            return result.filterNotNull()
        }

        suspend fun addPost(newPost: Post): Boolean {
            val id = post.document().id

            newPost.id = id

            post.document(id).set(newPost).await()
            return true
        }
    }

    suspend fun addImage(images: List<ImageInfo>): Boolean {
        this.image + images

        this.image.forEach {
            if(it.userId == "") it.userId = uid!!
            if(it.postId == "") it.postId = id!!
        }

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
            "lat" to updatePost.lat,
            "lng" to updatePost.lng,
            "addressAlias" to updatePost.addressAlias,
            "comment" to updatePost.comment,
            "image" to updatePost.image
        )

        update.filter { it.value != "" &&
                (it.value as List<*>).isNotEmpty() }
        post.update(update).await()

        return true
    }

    suspend fun delete(): Boolean {
        post.document(this.id!!)
            .delete().await()
        return true
    }
}
