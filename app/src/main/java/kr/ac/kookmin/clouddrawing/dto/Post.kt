package kr.ac.kookmin.clouddrawing.dto

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
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
        fun getPostById(id: String): Post? {
            var result: Post? = null
            post.document(id).get()
                .addOnSuccessListener { result = it.toObject<Post>() }
            return result
        }

        fun getPostByUID(uid: String): List<Post> {
            val result = mutableListOf<Post>()

            post.whereEqualTo("uid", uid).orderBy("writeTime")
                .get()
                .addOnSuccessListener {
                    it.documents.forEach { i ->
                        i.toObject<Post>()?.let { j -> result.add(j) }
                    }
                }
            return result
        }

        fun addPost(newPost: Post): Boolean {
            val id = post.document().id
            var result: Boolean = false

            newPost.id = id

            post.document(id).set(newPost)
                .addOnSuccessListener { result = true }
            return result
        }
    }

    fun addImage(images: List<ImageInfo>): Boolean {
        var result = false
        this.image + images

        this.image.forEach {
            if(it.userId == "") it.userId = uid!!
            if(it.postId == "") it.postId = id!!
        }

        post.document(this.id!!)
            .update("image", this.image)
            .addOnSuccessListener { result = true }
        return result
    }

    fun removeImage(images: List<ImageInfo>): Boolean {
        var result = false

        images.forEach {
            if(it.userId == "") it.userId = uid!!
            if(it.postId == "") it.postId = id!!
        }

        post.document(this.id!!)
            .update("image", FieldValue.arrayRemove(images))
            .addOnSuccessListener { result = true }
        return result
    }

    fun update(updatePost: Post): Boolean {
        var result = true

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
        post.update(update)
            .addOnSuccessListener { result = true }

        return result
    }

    fun delete(): Boolean {
        var result = false
        post.document(this.id!!)
            .delete()
            .addOnSuccessListener { result = true }
        return result
    }
}
