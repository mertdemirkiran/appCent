package com.demirkiran.baseApp.repository

import com.demirkiran.baseApp.api.RetrofitInstance
import javax.inject.Inject
import javax.inject.Singleton
@Singleton
class MainRepository @Inject constructor(){


    suspend fun getOpportunity() = RetrofitInstance.appcent_api.getOpportunity()

    suspend fun getCuriosity() = RetrofitInstance.appcent_api.getCuriosity()

    suspend fun getSpirit() = RetrofitInstance.appcent_api.getSpirit()
}