package com.example.test.service.http

import com.example.test.data.model.InvoiceSaleItem
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import io.reactivex.Single
import retrofit2.http.*

interface ApiServiceTiana {

    @GET("tiana/api/invoice/finalizedInformation?")
    fun getInvoiceSaleItem(
        @Query("InvoiceSN") invoiceSN: String
    ): Single<List<InvoiceSaleItem>>

}

fun createApiServiceTianaInstance(): ApiServiceTiana {

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            val oldRequest = it.request()
            val newRequestBuilder = oldRequest.newBuilder()
            newRequestBuilder.addHeader(
                "Authorization",
                "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZmllciI6IjAuMTMzIiwiaXNzIjoiOTQuMTgzLjIuNzkiLCJhdWQiOiI5NC4xODMuMi43OSJ9.X1_vb4VTGEePtcLUL2OZ_TcHQDGOHYkT5D_r0l9bG34")
            return@addInterceptor it.proceed(newRequestBuilder.build())
        }.build()

    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.20:19745/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()


    return retrofit.create(ApiServiceTiana::class.java)
}