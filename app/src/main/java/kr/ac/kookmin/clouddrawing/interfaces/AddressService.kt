package kr.ac.kookmin.clouddrawing.interfaces

import kr.ac.kookmin.clouddrawing.models.coord2address
import kr.ac.kookmin.clouddrawing.models.keyward2address
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

    @GET("v2/local/search/keyword")
    fun getLocation(
        @Header("Authorization") key: String,
        @Query("query") query: String,
        @Query("x") longitude: String? = null,
        @Query("y") latitude : String? = null,
        @Query("sort") sort : String = "accuracy"
    ) : Call<keyward2address>
}