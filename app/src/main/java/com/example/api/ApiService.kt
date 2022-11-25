package com.example.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*


interface ApiService {
    @GET("/Offres")
    suspend fun getOffres():Response<MutableList<Offre>>

    @GET("Offres/{code}")
    suspend fun getOffre(@Path("code") code : Int?):Response<Offre>

    @POST("/Offres")
    fun addOffre(@Body info: Offre): retrofit2.Call<ResponseBody>

    @PUT("Offres/{code}")
    suspend fun updateOffre(@Body info: Offre, @Path("code") code : Int?):Response<ResponseBody>

    @DELETE("Offres/{code}")
    suspend fun deleteOffres(@Path("code") code : Int?):Response<ResponseBody>


   // @DELETE("Offres/2")
 // suspend fun deleteOffres():Response<MutableList<Offre>>
}