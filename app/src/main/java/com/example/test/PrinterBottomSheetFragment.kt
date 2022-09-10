package com.example.test

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.data.model.PrinterDevice
import com.example.test.data.model.PrinterModelFile
import com.example.test.databinding.FragmentPrinterBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class PrinterBottomSheetFragment(val printEventListener: PrintEventListener) : BottomSheetDialogFragment(),
    PrinterAdapter.PrinterEventListener {

    private lateinit var binding: FragmentPrinterBottomSheetBinding
    private val sharedViewModel: PrinterViewModel by viewModel()

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
        printEventListener.print(printerDevice,PrinterModelFile.InvoiceSaleItem)

    }
    interface PrintEventListener{
        fun print(printerDevice: PrinterDevice,printerModelFile: PrinterModelFile);
    }


}
