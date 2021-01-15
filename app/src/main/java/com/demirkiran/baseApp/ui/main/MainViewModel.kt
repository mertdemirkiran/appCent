package com.demirkiran.baseApp.ui.main

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.demirkiran.baseApp.BaseApplication
import com.demirkiran.baseApp.data.Picture
import com.demirkiran.baseApp.data.Pictures
import com.demirkiran.baseApp.repository.MainRepository
import com.demirkiran.baseApp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject


class MainViewModel (
    app: Application,
    private val mainRepository: MainRepository
) : AndroidViewModel(app) {

    val allOpportunity: MutableLiveData<Resource<Pictures>> = MutableLiveData()
    val allCuriosity: MutableLiveData<Resource<Pictures>> = MutableLiveData()
    val allSpirit: MutableLiveData<Resource<Pictures>> = MutableLiveData()

    var allOpportunityResponse: Pictures? = null
    var allCuriosityResponse: Pictures? = null
    var allSpiritResponse: Pictures? = null



    /**
     * Functions below this expression are called from fragment
     */


    fun allOpportunityFun() = viewModelScope.launch {
        safeOpportunityCall()
    }

    fun allCuriosityFun() = viewModelScope.launch {
        safeCuriosityCall()
    }

    fun allSpiritFun() = viewModelScope.launch {
        safeSpiritCall()
    }



    /**
     * Safe network calls
     */

    private suspend fun safeOpportunityCall() {
        //allOpportunity.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = mainRepository.getOpportunity()
                allOpportunity.postValue(handleOpportunityResponse(response))
            } else {
                allOpportunity.postValue(Resource.Error("İnternet bağlantısı yok!"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> allOpportunity.postValue(Resource.Error("Network Hatası"))
                else -> allOpportunity.postValue(Resource.Error("Bir hata oluştu, yeniden deneyiniz. \n $t"))
            }
        }
    }

    private suspend fun safeCuriosityCall() {
        //allOpportunity.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = mainRepository.getCuriosity()
                allCuriosity.postValue(handleCuriosityResponse(response))
            } else {
                allCuriosity.postValue(Resource.Error("İnternet bağlantısı yok!"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> allCuriosity.postValue(Resource.Error("Network Hatası"))
                else -> allCuriosity.postValue(Resource.Error("Bir hata oluştu, yeniden deneyiniz. \n $t"))
            }
        }
    }

    private suspend fun safeSpiritCall() {
        //allOpportunity.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = mainRepository.getSpirit()
                allSpirit.postValue(handleCuriosityResponse(response))
            } else {
                allSpirit.postValue(Resource.Error("İnternet bağlantısı yok!"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> allSpirit.postValue(Resource.Error("Network Hatası"))
                else -> allSpirit.postValue(Resource.Error("Bir hata oluştu, yeniden deneyiniz. \n $t"))
            }
        }
    }



    /**
     * Transferring data
     */


    private fun handleOpportunityResponse(response: Response<Pictures>): Resource<Pictures> {
        if (response.isSuccessful /*&& response.body()!!.total > 0*/) {
            response.body()?.let { resultResponse ->
                return Resource.Success(allOpportunityResponse ?: resultResponse)
            }
        }
        return Resource.Error("Kullanıcılar bulunamadı.")
    }

    private fun handleCuriosityResponse(response: Response<Pictures>): Resource<Pictures> {
        if (response.isSuccessful /*&& response.body()!!.total > 0*/) {
            response.body()?.let { resultResponse ->
                return Resource.Success(allCuriosityResponse ?: resultResponse)
            }
        }
        return Resource.Error("Kullanıcılar bulunamadı.")
    }
    private fun handleSpiritResponse(response: Response<Pictures>): Resource<Pictures> {
        if (response.isSuccessful /*&& response.body()!!.total > 0*/) {
            response.body()?.let { resultResponse ->
                return Resource.Success(allSpiritResponse ?: resultResponse)
            }
        }
        return Resource.Error("Kullanıcılar bulunamadı.")
    }



    /**
     * Internet Connection Check.
     * This func works all request
     */
    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<BaseApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities =
            connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}