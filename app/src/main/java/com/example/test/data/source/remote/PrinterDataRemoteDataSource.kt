package com.example.test.data.source.remote

import com.example.test.data.model.InvoiceSaleItem
import com.example.test.data.repository.PrinterDataRepository
import com.example.test.data.source.PrinterDataDataSource
import com.example.test.service.http.ApiServiceTiana
import io.reactivex.Single

class PrinterDataRemoteDataSource(private val apiServiceTiana: ApiServiceTiana) :
    PrinterDataDataSource {
    override fun getInvoiceSaleItem(invoiceSN: String): Single<List<InvoiceSaleItem>> =
        apiServiceTiana.getInvoiceSaleItem(invoiceSN)
}