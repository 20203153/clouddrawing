package kr.ac.kookmin.clouddrawing.models

data class Address(
    val address_name : String,
    val region_1depth_name : String,
    val region_2depth_name : String,
    val mountain_yn : String,
    val main_address_no : String,
    val sub_address_no : String
)
