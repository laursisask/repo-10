package nl.mranderson.rijks.data.api

import CollectionResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CollectionApiService {

    @GET("/api/nl/collection?s=artist&imgonly=true")
    suspend fun getCollection(@Query("p") page: Int, @Query("ps") loadSize: Int): CollectionResponse

}