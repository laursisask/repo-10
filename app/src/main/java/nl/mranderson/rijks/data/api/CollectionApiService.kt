package nl.mranderson.rijks.data.api

import nl.mranderson.rijks.data.model.ArtDetailResponse
import nl.mranderson.rijks.data.model.CollectionResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CollectionApiService {

    @GET("/api/nl/collection?s=artist&imgonly=true")
    suspend fun getCollection(@Query("p") page: Int, @Query("ps") loadSize: Int): CollectionResponse

    @GET("/api/nl/collection/{artId}")
    suspend fun getArtDetails(@Path("artId") artId: String): ArtDetailResponse

}