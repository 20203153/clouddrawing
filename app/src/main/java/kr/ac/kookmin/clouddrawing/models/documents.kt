package kr.ac.kookmin.clouddrawing.models

data class documentsByCoord(
    val address : Address,
    val road_address : RoadAddress
)

data class documentsByKeyward(
    val id: String,
    val x: String,
    val y: String
)