package com.example.test
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bixolonprinter.data.model.PrinterDevice



class PrinterViewModel : ViewModel() {


    val bluetoothDevicesLiveData = MutableLiveData<ArrayList<PrinterDevice>>()
    val connectDevicesLiveData = MutableLiveData<PrinterDevice?>()


    init {
        cleanBluetoothDevicesLiveData()

    }

    fun cleanBluetoothDevicesLiveData(){
        bluetoothDevicesLiveData.value = ArrayList()

    }

    fun addBluetoothDevice(device: PrinterDevice) {
        if (bluetoothDevicesLiveData.value?.contains(device) == false)
            bluetoothDevicesLiveData.value?.add(device)
    }

    fun addConnectBluetoothDevice(device: PrinterDevice) {
        connectDevicesLiveData.value= device
    }

}