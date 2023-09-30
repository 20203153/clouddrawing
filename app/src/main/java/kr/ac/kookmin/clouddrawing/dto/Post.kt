package kr.ac.kookmin.clouddrawing.dto

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import java.util.Date

data class Post(
    val id: String? = "",
    val uid: String? = "",
    val lat: Float? = 0.0F,
    val lng: Float? = 0.0F,
    val addressAlias: String? = "",
    val comment: String? = "",
    val image: List<ImageInfo> = listOf(),
    val writeTime: Date?
) {
    companion object {
        private val post = FirebaseFirestore.getInstance().collection("post")
        fun getPostById(id: String): Post? {
            var result: Post? = null
            post.document(id).get()
                .addOnCompleteListener {
                    result = it.result.toObject<Post>()
                }
            return result
        }

        fun getPostByUID(uid: String): List<Post> {
            val result = mutableListOf<Post>()
            post.whereEqualTo("uid", uid).orderBy("writeTime")
                .get()
                .addOnCompleteListener {
                    it.result.documents.forEach { i ->
                        i.toObject<Post>()?.let { j -> result.add(j) }
                    }
                }
            return result
        }

        fun addPost(newPost: Post): Boolean {
            var result: Boolean = false
            post.add(newPost)
                .addOnCompleteListener { result = true }
            return result
        }
    }

    fun addImage(images: List<ImageInfo>): Boolean {
        var result = false
        this.image + images
        post.document(this.id!!)
            .update("image", this.image)
            .addOnCompleteListener {
                result = true
            }
        return result
    }

    fun removeImage(images: List<ImageInfo>): Boolean {
        var result = false
        post.document(this.id!!)
            .update("image", FieldValue.arrayRemove(images))
            .addOnCompleteListener {
                result = true
            }
        return result
    }

    fun update(updatePost: Post): Boolean {
        val result = false

        val post = post.document(this.id!!)

        if(updatePost.lat != null) post.update("lat", updatePost.lat)
        if(updatePost.lng != null) post.update("lng", updatePost.lng)
        if(updatePost.addressAlias != null) post.update("addressAlias", updatePost.addressAlias)
        if(updatePost.comment != null) post.update("comment", updatePost.comment)
        if(updatePost.image.isNotEmpty()) post.update("image", FieldValue.arrayUnion(updatePost.image))

        return result
    }

    fun delete(): Boolean {
        var result = false
        post.document(this.id!!)
            .delete()
            .addOnCompleteListener {
                result = true
            }
        return result
    }
}
