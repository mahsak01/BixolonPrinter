package com.example.test

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.test.data.model.PrinterDevice
import com.example.test.databinding.LayoutPrinterBottomSheetItemBinding

class PrinterAdapter(private val printerList: MutableList<PrinterDevice>, val printerEventListener: PrinterEventListener) : RecyclerView.Adapter<PrinterAdapter.ViewHolder>()  {


    inner class ViewHolder(private val binding: LayoutPrinterBottomSheetItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindingServer(printerDevice: PrinterDevice){
            binding.layoutPrinterPrinterTv.text = printerDevice.name

            binding.layoutPrinterPrinterTv.isSelected=true

            binding.layoutPrinterPrinterCv.setOnClickListener{
              printerEventListener.selectPrinter(printerDevice)
            }




        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.layout_printer_bottom_sheet_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindingServer(printerList[position])
    }

    override fun getItemCount(): Int =printerList.size

    interface PrinterEventListener{
        fun selectPrinter(printerDevice: PrinterDevice)
    }


}