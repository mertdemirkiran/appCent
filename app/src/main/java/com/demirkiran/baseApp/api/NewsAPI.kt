package com.demirkiran.baseApp.api

import com.demirkiran.baseApp.data.Picture
import com.demirkiran.baseApp.data.Pictures

import com.demirkiran.baseApp.util.Constants
import retrofit2.Response
import retrofit2.http.*

interface NewsAPI {



    @GET(Constants.getOpportunity)
    suspend fun getOpportunity(): Response<Pictures>

    @GET(Constants.getCuriosity)
    suspend fun getCuriosity(): Response<Pictures>

    @GET(Constants.getSpirit)
    suspend fun getSpirit(): Response<Pictures>


}