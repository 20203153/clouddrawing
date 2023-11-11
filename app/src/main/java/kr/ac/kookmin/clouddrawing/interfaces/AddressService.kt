package kr.ac.kookmin.clouddrawing.interfaces

import kr.ac.kookmin.clouddrawing.models.coord2address
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface AddressService {
    @GET("v2/local/geo/coord2address")
    fun getAddress(
        @Header("Authorization") key : String,
        @Query("x") longitude : String,
        @Query("y") latitude : String
    ) : Call<coord2address>
}