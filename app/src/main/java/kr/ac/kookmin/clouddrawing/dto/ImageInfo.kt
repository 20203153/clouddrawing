package kr.ac.kookmin.clouddrawing.dto

import android.annotation.SuppressLint
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

data class ImageInfo(
    val id: String = "",
    val userId: String = "",
    val postId: String = "",
    val imageURI: String = ""
) {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private val db = FirebaseFirestore.getInstance()
        private val imageInfo = db.collection("ImageInfo")

        fun getImageById(id: String): ImageInfo? {
            var result: ImageInfo? = null
            imageInfo.document(id).get()
                .addOnCompleteListener {
                    result = it.result.toObject<ImageInfo>()
                }

            return result
        }

        fun getImageByPostId(postId: String): List<ImageInfo> {
            val result: MutableList<ImageInfo> = mutableListOf()
            imageInfo.whereEqualTo("postId", postId).orderBy("imageURI")
                .get()
                .addOnCompleteListener {
                    it.result.documents.forEach {i ->
                        i.toObject<ImageInfo>()?.let { j -> result.add(j) }
                    }
                }
            return result
        }

        fun addImage(image: ImageInfo): Boolean {
            var result = false
            imageInfo.add(image)
                .addOnCompleteListener { result = true }

            return result
        }
    }
}