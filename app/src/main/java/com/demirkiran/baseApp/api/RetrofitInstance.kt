package com.demirkiran.baseApp.api

import com.demirkiran.baseApp.util.Constants.Companion.BASE_URL
import com.demirkiran.baseApp.util.Constants.Companion.BASE_URL_NASA
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitInstance {
    companion object {
        private val retrofit by lazy {

            val client = OkHttpClient.Builder()

                .addInterceptor{ chain ->
                    val original = chain.request()
                    val request = original.newBuilder()
                            .addHeader("app-id", "5fd631bcafa09213eb0588ef")
                            .method(original.method, original.body)
                            .build()

                    chain.proceed(request)
                }
                .addNetworkInterceptor(StethoInterceptor())
                .build()

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val api: NewsAPI by lazy {
            retrofit.create(NewsAPI::class.java)
        }

        private val appcent_retrofit by lazy {

            val client = OkHttpClient.Builder()
                .addInterceptor{ chain ->
                    val original = chain.request()
                    var request = original.newBuilder()
                        //.addHeader("API_KEY", "eB95xvwtWmHWSwirvLdplIShb6o29Rwb5USXgLAR")
                        .method(original.method, original.body)
                        .build()
                    val url = request.url.newBuilder().addQueryParameter("api_key", "eB95xvwtWmHWSwirvLdplIShb6o29Rwb5USXgLAR")
                        .addQueryParameter("sol","1000")
                        .build()
                    request= request.newBuilder().url(url).build();
                    chain.proceed(request)
                }
                .addNetworkInterceptor(StethoInterceptor())
                .build()

            Retrofit.Builder()
                .baseUrl(BASE_URL_NASA)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val appcent_api: NewsAPI by lazy {
            appcent_retrofit.create(NewsAPI::class.java)
        }


    }
}