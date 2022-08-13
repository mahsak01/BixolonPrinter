package com.example.bixolonprinter.data.model

import android.bluetooth.BluetoothClass
import android.bluetooth.BluetoothDevice
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PrinterDevice(
    val hardwareAddress:String,
    val device: BluetoothDevice,
    val name: String
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PrinterDevice

        if (hardwareAddress != other.hardwareAddress) return false

        return true
    }

    override fun hashCode(): Int {
        return hardwareAddress.hashCode()
    }
}