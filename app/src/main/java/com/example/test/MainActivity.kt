package com.example.test

import InvoiceDeliveryReceipt
import InvoiceSale
import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.bxl.config.editor.BXLConfigLoader
import com.example.test.data.model.PrinterDevice
import com.example.test.data.model.PrinterModelFile
import com.example.test.databinding.ActivityMainBinding
import jpos.POSPrinter
import jpos.POSPrinterConst
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDateTime


class MainActivity : AppCompatActivity(), PrinterBottomSheetFragment.PrintEventListener {


    private val sharedViewModel: PrinterViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        pairBluetoothDevice()
        val filter = IntentFilter()
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
        this.registerReceiver(broadcastReceiver, filter)
        setObserver()


        val bottomSheetDialog = PrinterBottomSheetFragment(this)

        this.binding.activityMainPrinterBtn.setOnClickListener {
            pairBluetoothDevice()
            bottomSheetDialog.show(supportFragmentManager, "bottomSheetDialog")
        }
        this.binding.activityMainSharePdfBtn.setOnClickListener {
            share(PrinterModelFile.InvoiceSaleItem)
        }
    }

    private fun pairBluetoothDevice() {
        val bAdapter = BluetoothAdapter.getDefaultAdapter()
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            if (bAdapter.bondedDevices.size > 0) {
                sharedViewModel.cleanBluetoothDevicesLiveData()
                for (device in bAdapter.bondedDevices) {
                    if (device.bluetoothClass.toString() == "40680")
                        sharedViewModel.addBluetoothDevice(
                            PrinterDevice(
                                device!!.address,
                                device,
                                device.name
                            )
                        )

                }
            }
            return
        } else {

        }


    }

    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        var device: BluetoothDevice? = null
        override fun onReceive(
            context: Context,
            intent: Intent
        ) {
            val action = intent.action
            device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
            if (BluetoothDevice.ACTION_ACL_CONNECTED == action) {
                Toast.makeText(
                    baseContext,
                    "Device is now Connected",
                    Toast.LENGTH_SHORT
                ).show()
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.BLUETOOTH_CONNECT
                    ) != PackageManager.PERMISSION_GRANTED
                ) {

                    if (device!!.bluetoothClass.toString() == "40680")
                        sharedViewModel.addBluetoothDevice(
                            PrinterDevice(
                                device!!.address,
                                device!!,
                                device!!.name
                            )
                        )
                    return
                }
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun setObserver() {
        sharedViewModel.invoiceSaleItemsPrintLiveData.observe(this) {
            if (it != null) {
                val invoiceSale = InvoiceSale()
                val file = invoiceSale.createTable(
                    this,
                    "/printInvoice" + LocalDateTime.now() + ".pdf",
                    it
                )
                Thread {
                    val bxlConfigLoader = BXLConfigLoader(this)
                    try {
                        bxlConfigLoader.openFile()
                        bxlConfigLoader.addEntry(
                            sharedViewModel.connectDevicesLiveData.value?.name,
                            BXLConfigLoader.DEVICE_CATEGORY_POS_PRINTER,
                            sharedViewModel.connectDevicesLiveData.value?.name?.let { it ->
                                getDeviceNameProduct(
                                    it
                                )
                            },
                            BXLConfigLoader.DEVICE_BUS_BLUETOOTH,
                            sharedViewModel.connectDevicesLiveData.value?.hardwareAddress
                        )
                        bxlConfigLoader.saveFile()
                    } catch (e: Exception) {
                        bxlConfigLoader.newFile()
                        bxlConfigLoader.addEntry(
                            sharedViewModel.connectDevicesLiveData.value?.name,
                            BXLConfigLoader.DEVICE_CATEGORY_POS_PRINTER,
                            sharedViewModel.connectDevicesLiveData.value?.name?.let {
                                getDeviceNameProduct(
                                    it
                                )
                            },
                            BXLConfigLoader.DEVICE_BUS_BLUETOOTH,
                            sharedViewModel.connectDevicesLiveData.value?.hardwareAddress

                        )
                        bxlConfigLoader.saveFile()
                    }

                    try {
                        val posPrinter = POSPrinter(this)
                        posPrinter.open(sharedViewModel.connectDevicesLiveData.value?.name)
                        posPrinter.claim(5000)
                        posPrinter.deviceEnabled = true
                        val uri = Uri.fromFile(file)
                        runOnUiThread {
                            this.binding.activityMainPrinterBtn.isEnabled = false
                            Toast.makeText(
                                baseContext,
                                "دستور چاپ ارسال شد",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        posPrinter.printPDFFile(
                            POSPrinterConst.PTR_S_RECEIPT,
                            uri,
                            600,
                            POSPrinterConst.PTR_PDF_LEFT,
                            1
                        )
                        posPrinter.close()
                        runOnUiThread {
                            this.binding.activityMainPrinterBtn.isEnabled = true
                        }
                    } catch (e: Exception) {
                        runOnUiThread {
                            Toast.makeText(
                                baseContext,
                                "Device is not available",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }


                }.start()
            }
        }
        sharedViewModel.invoiceSaleItemsShareLiveData.observe(this) {
            if (it != null) {
                val invoiceSale = InvoiceSale()
                val file = invoiceSale.createTable(
                    this,
                    "/printInvoice" + LocalDateTime.now() + ".pdf",
                    it
                )
                val shareIntent = Intent("android.intent.action.SEND")
                val shareFileUri =
                    FileProvider.getUriForFile(this, "${BuildConfig.APPLICATION_ID}.provider", file)

                shareIntent.putExtra(Intent.EXTRA_STREAM, shareFileUri)
                shareIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                shareIntent.type = "application/pdf"
                startActivity(Intent.createChooser(shareIntent, "share.."))
            }
        }

    }

    override fun print(printerDevice: PrinterDevice, printerModelFile: PrinterModelFile) {
        if (printerModelFile == PrinterModelFile.InvoiceSaleItem)
            sharedViewModel.getInvoiceSaleItemsPrintLiveData("1.123")
    }

    private fun share( ShareModelFile: PrinterModelFile) {
        if (ShareModelFile == PrinterModelFile.InvoiceSaleItem)
            sharedViewModel.getInvoiceSaleItemsShareLiveData("1.123")

    }

    private fun getDeviceNameProduct(deviceName: String): String {
        return when (deviceName) {
            "SPP-R310" -> BXLConfigLoader.PRODUCT_NAME_SPP_R310

            "SPP-R300" -> BXLConfigLoader.PRODUCT_NAME_SPP_R300

            else -> BXLConfigLoader.PRODUCT_NAME_SPP_R310
        }
    }


}
