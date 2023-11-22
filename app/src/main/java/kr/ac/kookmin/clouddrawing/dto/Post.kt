package kr.ac.kookmin.clouddrawing.dto

import com.google.firebase.Timestamp
import com.google.firebase.firestore.*
import kotlinx.coroutines.tasks.await
import java.util.*

data class Post(
    var id: String? = "",
    var uid: String? = "",
    var title: String? = "",
    var lat: Double? = 0.0,
    var lng: Double? = 0.0,
    var address: String? = "",
    var addressAlias: String? = "",
    var comment: String? = "",
    var image: MutableList<String> = mutableListOf(),
    var writeTime: Timestamp? = Timestamp.now(),
    var postTime: Timestamp? = Timestamp.now()
) {
    companion object {
        private val post = FirebaseFirestore.getInstance().collection("post")

        suspend fun getAllPost(): List<Post> {
            val result = mutableListOf<Post?>()
            val allPost = post.get().await()

            allPost.forEach {
                result.add(it.toObject())
            }

            return result.filterNotNull()
        }

        suspend fun getPostById(id: String): Post? {
            return post.document(id).get().await().toObject()
        }

        suspend fun getPostByUID(uid: String?, limit: Long = 10L): List<Post> {
            val result = mutableListOf<Post?>()

            val posts = post.whereEqualTo("uid", uid)
                .orderBy("writeTime", Query.Direction.DESCENDING)
                .limit(limit)
                .get().await()

            posts.forEach { result.add(it.toObject()) }
            return result.filterNotNull()
        }

        suspend fun getPostByLatLng(uid: String, lat: Double, lng: Double): List<Post> {
            val result = mutableListOf<Post?>()
            val posts = post.whereEqualTo("uid", uid)
                .whereEqualTo("lat", lat)
                .whereEqualTo("lng", lng)
                .orderBy("writeTime", Query.Direction.DESCENDING)
                .get().await()
            posts.forEach { result.add(it.toObject()) }
            return result.filterNotNull()

        }

        suspend fun getPostCountByDate(uid: String, date: Date = Date()): Int {
            val result = post.whereEqualTo("uid", uid)
                .whereGreaterThanOrEqualTo("writeTime", Timestamp(date))
                .count().get(AggregateSource.SERVER).await()
            return result.count.toInt()
        }

        suspend fun addPost(newPost: Post): String {
            newPost.id.let { if(it == null) post.document().id }

            post.document(newPost.id!!).set(newPost).await()
            return newPost.id!!
        }

        fun getNewPostId(): String {
            return post.document().id
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
            "address" to updatePost.address,
            "addressAlias" to updatePost.addressAlias,
            "comment" to updatePost.comment,
            "image" to updatePost.image
        )
        update.filter { it.value != null }

        post.update(update).await()

        return true
    }

    suspend fun delete(): Boolean {
        post.document(this.id!!)
            .delete().await()
        return true
    }
}
