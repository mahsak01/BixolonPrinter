package com.example.test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test.data.model.InvoiceSaleItem
import com.example.test.data.model.PrinterDevice
import com.example.test.data.repository.PrinterDataRepository
import com.example.tianaserver.common.TianaSingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class PrinterViewModel(private val printerDataRepository: PrinterDataRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val bluetoothDevicesLiveData = MutableLiveData<ArrayList<PrinterDevice>>()
    val connectDevicesLiveData = MutableLiveData<PrinterDevice?>()
    val invoiceSaleItemsPrintLiveData = MutableLiveData<List<InvoiceSaleItem>>()
    val invoiceSaleItemsShareLiveData = MutableLiveData<List<InvoiceSaleItem>>()


    init {
        cleanBluetoothDevicesLiveData()

    }

    fun cleanBluetoothDevicesLiveData() {
        bluetoothDevicesLiveData.value = ArrayList()

    }

    fun addBluetoothDevice(device: PrinterDevice) {
        if (bluetoothDevicesLiveData.value?.contains(device) == false)
            bluetoothDevicesLiveData.value?.add(device)
    }

    fun addConnectBluetoothDevice(device: PrinterDevice) {
        connectDevicesLiveData.value = device
    }

    fun getInvoiceSaleItemsPrintLiveData(invoiceSN: String) {
        printerDataRepository.getInvoiceSaleItem(invoiceSN)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : TianaSingleObserver<List<InvoiceSaleItem>>(compositeDisposable) {
                override fun onSuccess(t: List<InvoiceSaleItem>) {
                    invoiceSaleItemsPrintLiveData.value = t
                }

            })

    }
    fun getInvoiceSaleItemsShareLiveData(invoiceSN: String) {
        printerDataRepository.getInvoiceSaleItem(invoiceSN)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : TianaSingleObserver<List<InvoiceSaleItem>>(compositeDisposable) {
                override fun onSuccess(t: List<InvoiceSaleItem>) {
                    invoiceSaleItemsShareLiveData.value = t
                }

            })

    }
}