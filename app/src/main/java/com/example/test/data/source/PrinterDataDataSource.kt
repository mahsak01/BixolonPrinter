package com.example.test.data.source

import com.example.test.data.model.InvoiceSaleItem
import io.reactivex.Single
import retrofit2.http.Query

interface PrinterDataDataSource {
    fun getInvoiceSaleItem(invoiceSN: String): Single<List<InvoiceSaleItem>>
}