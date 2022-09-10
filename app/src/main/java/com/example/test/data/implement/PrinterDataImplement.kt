package com.example.test.data.implement

import com.example.test.data.model.InvoiceSaleItem
import com.example.test.data.repository.PrinterDataRepository
import com.example.test.data.source.PrinterDataDataSource
import io.reactivex.Single

class PrinterDataImplement(private val printerDataRemoteDataSource: PrinterDataDataSource) :
    PrinterDataRepository {
    override fun getInvoiceSaleItem(invoiceSN: String): Single<List<InvoiceSaleItem>> =
        printerDataRemoteDataSource.getInvoiceSaleItem(invoiceSN)
}