package com.example.test.data.repository

import com.example.test.data.model.InvoiceSaleItem
import io.reactivex.Single
import retrofit2.http.Query

interface PrinterDataRepository {
    fun getInvoiceSaleItem(invoiceSN: String): Single<List<InvoiceSaleItem>>
}