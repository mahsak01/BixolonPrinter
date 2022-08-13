package com.example.test

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bxl.config.editor.BXLConfigLoader
import com.example.bixolonprinter.data.model.PrinterDevice
import com.example.test.databinding.FragmentPrinterBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import jpos.POSPrinter
import jpos.POSPrinterConst
import org.koin.android.ext.android.inject
import java.lang.Exception


class PrinterBottomSheetFragment(val printEventListener: PrintEventListener) : BottomSheetDialogFragment(),
    PrinterAdapter.PrinterEventListener {

    private lateinit var binding: FragmentPrinterBottomSheetBinding
    private val sharedViewModel: PrinterViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onResume() {
        super.onResume()
        binding.activityMainPrintersBottomSheetRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.activityMainPrintersBottomSheetRv.adapter =
            PrinterAdapter(
                sharedViewModel.bluetoothDevicesLiveData.value as MutableList<PrinterDevice>,
                this
            )

        sharedViewModel.bluetoothDevicesLiveData.observe(viewLifecycleOwner) {
            binding.activityMainPrintersBottomSheetRv.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            binding.activityMainPrintersBottomSheetRv.adapter =
                PrinterAdapter(it as MutableList<PrinterDevice>, this)
        }

        binding.activityMainAddPrinterBtn.setOnClickListener {
            dismiss()
            startActivity(Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS))
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_printer_bottom_sheet,
            container,
            false
        )

        return binding.root
    }

    override fun selectPrinter(printerDevice: PrinterDevice) {
        sharedViewModel.addConnectBluetoothDevice(printerDevice)
        dismiss()
        printEventListener.print(printerDevice)

    }
    interface PrintEventListener{
        fun print(printerDevice: PrinterDevice);
    }


}
