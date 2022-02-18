package dev.illwiz.tada.data.repository.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArtAPI {

    @GET("collection")
    suspend fun getArtCollection(
        @Query("p") pageNumber:Int = 0,
        @Query("ps") pageSize:Int = 10
    ): GetArtCollectionResponse

    @GET("collection/{object-number}")
    suspend fun getArt(
        @Path("object-number") objectNumber:String
    ): GetArtResponse
}