import com.itextpdf.text.*
import com.itextpdf.text.pdf.*


class TableGenerator {

    var totalHeight = 0F

    fun imageCenter(path: String): Image {

        var image = Image.getInstance(path)

        image.alignment = Element.ALIGN_CENTER

        image.scaleToFit(70F, 70F)

        totalHeight += 70

        return image
    }
    fun textTableWith2Column(text1: String,text2: String,font: Font):PdfPTable{

        val table = PdfPTable(2)
        table.runDirection = PdfWriter.RUN_DIRECTION_RTL

        val paragraph1 = Paragraph("$text1", font)
        paragraph1.alignment = Element.ALIGN_MIDDLE
        val headCellTitle1 = PdfPCell(paragraph1)
        headCellTitle1.horizontalAlignment = Element.ALIGN_LEFT
        headCellTitle1.verticalAlignment = Element.ALIGN_CENTER
        headCellTitle1.setPadding(5F)
        headCellTitle1.borderWidthLeft=0F
        headCellTitle1.borderWidthBottom=0F
        headCellTitle1.borderWidthTop=0F
        table.addCell(headCellTitle1)

        val paragraph2 = Paragraph("$text2", font)
        paragraph2.alignment = Element.ALIGN_CENTER
        val headCellTitle2 = PdfPCell(paragraph2)
        headCellTitle2.horizontalAlignment = Element.ALIGN_LEFT
        headCellTitle2.verticalAlignment = Element.ALIGN_CENTER
        headCellTitle2.setPadding(5F)
        headCellTitle2.borderWidthRight=0F
        headCellTitle2.borderWidthBottom=0F
        headCellTitle2.borderWidthTop=0F
        table.addCell(headCellTitle2)

        table.widthPercentage = 90F
        table.totalWidth = 205F
        table.isLockedWidth = true
        totalHeight += table.totalHeight
        return table
    }

    fun textTableWith1Column(text1: String,font: Font):PdfPTable{

        val table = PdfPTable(1)
        table.runDirection = PdfWriter.RUN_DIRECTION_RTL

        val paragraph1 = Paragraph("$text1", font)
        paragraph1.alignment = Element.ALIGN_MIDDLE
        val headCellTitle1 = PdfPCell(paragraph1)
        headCellTitle1.horizontalAlignment = Element.ALIGN_LEFT
        headCellTitle1.verticalAlignment = Element.ALIGN_CENTER
        headCellTitle1.setPadding(5F)
        headCellTitle1.borderWidthBottom=0F
        headCellTitle1.borderWidthTop=0F
        table.addCell(headCellTitle1)


        table.widthPercentage = 90F
        table.totalWidth = 205F
        table.isLockedWidth = true
        totalHeight += table.totalHeight
        return table
    }

    fun headerImageAndTitleLeft(title: String, path: String, font: Font): PdfPTable {
        var array = FloatArray(2)
        array[0] = 80F
        array[1] = 30F
        val table = PdfPTable(array)
        table.runDirection = PdfWriter.RUN_DIRECTION_RTL

//        var image = Image.getInstance("")
//
//        image.alignment = Element.ALIGN_CENTER
//
//        image.scaleToFit(40F, 40F)
        val paragraph2 = Paragraph("عکس", font)
        paragraph2.alignment = Element.ALIGN_CENTER
        val headCellImage = PdfPCell(paragraph2)
        headCellImage.horizontalAlignment = Element.ALIGN_LEFT
        headCellImage.verticalAlignment = Element.ALIGN_CENTER
        headCellImage.setPadding(5F)
        headCellImage.borderWidthLeft=0F

        table.addCell(headCellImage)

        val paragraph = Paragraph("$title", font)
        paragraph.alignment = Element.ALIGN_CENTER
        val headCellTitle = PdfPCell(paragraph)
        headCellTitle.horizontalAlignment = Element.ALIGN_CENTER
        headCellTitle.verticalAlignment = Element.ALIGN_CENTER
        headCellTitle.setPadding(5F)
        headCellTitle.borderWidthRight=0F


        table.addCell(headCellTitle)

        table.widthPercentage = 90F
        table.totalWidth = 205F
        table.isLockedWidth = true
        totalHeight += table.totalHeight
        return table
    }

    fun headerImageAndTitleRight(title: String, path: String, font: Font): PdfPTable {
        var array = FloatArray(2)
        array[0] = 80F
        array[1] = 30F
        val table = PdfPTable(array)
        table.runDirection = PdfWriter.RUN_DIRECTION_RTL
        val paragraph = Paragraph("$title", font)
        paragraph.alignment = Element.ALIGN_MIDDLE
        val headCellTitle = PdfPCell(paragraph)
        headCellTitle.horizontalAlignment = Element.ALIGN_LEFT
        headCellTitle.verticalAlignment = Element.ALIGN_CENTER
        headCellTitle.setPadding(5F)
        headCellTitle.borderWidthLeft=0F
        headCellTitle.borderWidthBottom=0F



        table.addCell(headCellTitle)

//        var image = Image.getInstance(path)
//
//        image.alignment = Element.ALIGN_CENTER
//
//        image.scaleToFit(80F, 100F)
//
//        val headCellImage = PdfPCell(image)
        //        var image = Image.getInstance("")
//
//        image.alignment = Element.ALIGN_CENTER
//
//        image.scaleToFit(40F, 40F)
        val paragraph2 = Paragraph("عکس", font)
        paragraph2.alignment = Element.ALIGN_CENTER
        val headCellImage = PdfPCell(paragraph2)
        headCellImage.horizontalAlignment = Element.ALIGN_LEFT
        headCellImage.verticalAlignment = Element.ALIGN_CENTER
        headCellImage.setPadding(5F)
        headCellImage.borderWidthRight=0F
        headCellImage.borderWidthBottom=0F

        table.addCell(headCellImage)



        table.widthPercentage = 90F
        table.totalWidth = 205F
        table.isLockedWidth = true
        totalHeight += table.totalHeight
        return table
    }

    fun textRightWithBorder(labelText: String, text: String, font: Font): PdfPTable {


        val table = PdfPTable(1)
        table.runDirection = PdfWriter.RUN_DIRECTION_RTL
        val paragraph = Paragraph("$labelText $text", font)
        paragraph.alignment = Element.ALIGN_LEFT
        val headCell = PdfPCell(paragraph)
        headCell.horizontalAlignment = Element.ALIGN_LEFT
        headCell.verticalAlignment = Element.ALIGN_CENTER
        headCell.setPadding(5F)

        table.addCell(headCell)
        table.widthPercentage = 90F
        table.totalWidth = 205F
        table.isLockedWidth = true
        totalHeight += table.totalHeight
        return table
    }

    fun textRightWithOutBorder(labelText: String, text: String, font: Font): PdfPTable {


        val table = PdfPTable(1)
        table.runDirection = PdfWriter.RUN_DIRECTION_RTL
        val paragraph = Paragraph("$labelText $text", font)
        paragraph.alignment = Element.ALIGN_LEFT
        val headCell = PdfPCell(paragraph)
        headCell.horizontalAlignment = Element.ALIGN_LEFT
        headCell.verticalAlignment = Element.ALIGN_CENTER
        headCell.setPadding(5F)
        headCell.border = PdfPCell.NO_BORDER
        table.addCell(headCell)
        table.widthPercentage = 90F
        table.totalWidth = 205F
        table.isLockedWidth = true
        totalHeight += table.totalHeight

        return table
    }

    fun textCenter(labelText: String, text: String, font: Font): PdfPTable {


        val table = PdfPTable(1)
        table.runDirection = PdfWriter.RUN_DIRECTION_RTL
        val paragraph = Paragraph("$labelText $text", font)
        paragraph.alignment = Element.ALIGN_CENTER
        val headCell = PdfPCell(paragraph)
        headCell.horizontalAlignment = Element.ALIGN_CENTER
        headCell.verticalAlignment = Element.ALIGN_CENTER
        headCell.setPadding(5F)
        headCell.border = PdfPCell.NO_BORDER
        table.addCell(headCell)
        table.widthPercentage = 70f
        table.totalWidth = 205F
        table.isLockedWidth = true
        totalHeight += table.totalHeight
        return table
    }

    fun textCenterWithBackground(labelText: String, text: String, font: Font): PdfPTable {


        val table = PdfPTable(1)
        table.runDirection = PdfWriter.RUN_DIRECTION_RTL
        val paragraph = Paragraph("$labelText $text", font)
        paragraph.alignment = Element.ALIGN_CENTER
        val headCell = PdfPCell(paragraph)
        headCell.horizontalAlignment = Element.ALIGN_CENTER
        headCell.verticalAlignment = Element.ALIGN_CENTER
        headCell.setPadding(5F)
        headCell.backgroundColor = BaseColor(173, 173, 173)
        headCell.border = PdfPCell.NO_BORDER
        table.addCell(headCell)
        table.widthPercentage = 100f
        table.totalWidth = 226f
        table.isLockedWidth = true
        totalHeight += table.totalHeight
        return table
    }

    fun textRowCenter(text1: String, text2: String, font: Font): PdfPTable {


        val table = PdfPTable(2)
        table.runDirection = PdfWriter.RUN_DIRECTION_RTL
        val paragraph1 = Paragraph("$text1", font)
        paragraph1.alignment = Element.ALIGN_CENTER
        val headCell = PdfPCell(paragraph1)
        headCell.horizontalAlignment = Element.ALIGN_LEFT
        headCell.verticalAlignment = Element.ALIGN_CENTER
        headCell.setPadding(5F)
        headCell.border = PdfPCell.NO_BORDER
        table.addCell(headCell)
        val paragraph2 = Paragraph("$text2", font)
        paragraph2.alignment = Element.ALIGN_CENTER
        val headCell2 = PdfPCell(paragraph2)
        headCell2.horizontalAlignment = Element.ALIGN_RIGHT
        headCell2.verticalAlignment = Element.ALIGN_CENTER
        headCell2.setPadding(5F)
        headCell2.border = PdfPCell.NO_BORDER
        table.addCell(headCell2)
        table.widthPercentage = 70f
        table.totalWidth = 205F
        table.isLockedWidth = true
        totalHeight += table.totalHeight
        return table
    }

    fun headTable(headText: String, itemsTable: List<String>?, font: Font, array: FloatArray): PdfPTable {


        val table = PdfPTable(array)
        table.widthPercentage = 90F
        table.runDirection = PdfWriter.RUN_DIRECTION_RTL
        var headParagraph = Paragraph(headText, font)
        headParagraph.alignment = Element.ALIGN_MIDDLE
        val headCell = PdfPCell(headParagraph)
        headCell.horizontalAlignment = Element.ALIGN_CENTER
        headCell.verticalAlignment = Element.ALIGN_CENTER
        headCell.colspan = 2
        headCell.setPadding(5F)
        table.addCell(headCell)

        itemsTable?.let { items ->
            items.forEach { item ->
                var text = Paragraph(item, font)
                text.alignment = Element.ALIGN_MIDDLE
                val cell = PdfPCell(text)
                cell.horizontalAlignment = Element.ALIGN_CENTER;
                cell.verticalAlignment = Element.ALIGN_CENTER;
                cell.setPadding(5F)
                table.addCell(cell)
            }
        }
        table.totalWidth = 205f
        table.isLockedWidth = true
        totalHeight += table.totalHeight
        return table

    }


    fun tableProduct(headText: String?, itemsTable: List<String>?, font: Font, array: FloatArray): PdfPTable {

        val table = PdfPTable(array)
        table.widthPercentage = 90F

        table.runDirection = PdfWriter.RUN_DIRECTION_RTL
        if (headText!=null){
            val headParagraph = Paragraph(headText, font)
            headParagraph.alignment = Element.ALIGN_MIDDLE
            val headCell = PdfPCell(headParagraph)
            headCell.horizontalAlignment = Element.ALIGN_CENTER
            headCell.verticalAlignment = Element.ALIGN_CENTER
            headCell.colspan = 6
            headCell.setPadding(5F)
            headCell.backgroundColor = BaseColor(173, 173, 173)
            table.addCell(headCell)
        }


        itemsTable?.let { items ->
            items.forEach { item ->
                var text = Paragraph(item, font)
                text.alignment = Element.ALIGN_MIDDLE
                val cell = PdfPCell(text)
                cell.horizontalAlignment = Element.ALIGN_CENTER;
                cell.verticalAlignment = Element.ALIGN_CENTER;
                cell.setPadding(5F)

                table.addCell(cell)
            }
        }

        table.totalWidth = 205F
        table.isLockedWidth = true
        totalHeight += table.totalHeight
        return table

    }


    fun footerTable(itemsTable: List<String>?, font: Font): PdfPTable {
        var array = FloatArray(2)
        array[0] = 40F
        array[1] = 80F

        val table = PdfPTable(array)
        table.runDirection = PdfWriter.RUN_DIRECTION_RTL
        table.widthPercentage = 90F

        itemsTable?.let { items ->
            items.forEach { item ->
                var text = Paragraph(item, font)
                if (items.indexOf(item) % 2 == 0) {
                    text.alignment = Element.ALIGN_LEFT
                    val cell = PdfPCell(text)
                    cell.horizontalAlignment = Element.ALIGN_LEFT
                    cell.verticalAlignment = Element.ALIGN_LEFT
                    cell.setPadding(5F)
                    table.addCell(cell)
                } else {
                    text.alignment = Element.ALIGN_MIDDLE
                    val cell = PdfPCell(text)
                    cell.horizontalAlignment = Element.ALIGN_CENTER
                    cell.verticalAlignment = Element.ALIGN_CENTER
                    cell.setPadding(5F)
                    table.addCell(cell)
                }


            }
        }
        table.totalWidth = 205F
        table.isLockedWidth = true
        totalHeight += table.totalHeight
        return table

    }

    fun footerTableWithText(text1: String,text2: String, font: Font): PdfPTable {
        val table = PdfPTable(2)
        table.runDirection = PdfWriter.RUN_DIRECTION_RTL

        val paragraph1 = Paragraph("$text1", font)
        paragraph1.alignment = Element.ALIGN_MIDDLE
        val headCellTitle1 = PdfPCell(paragraph1)
        headCellTitle1.horizontalAlignment = Element.ALIGN_LEFT
        headCellTitle1.verticalAlignment = Element.ALIGN_CENTER
        headCellTitle1.setPadding(5F)
        headCellTitle1.borderWidthLeft=0F
        headCellTitle1.borderWidthTop=0F
        table.addCell(headCellTitle1)

        val paragraph2 = Paragraph("$text2", font)
        paragraph2.alignment = Element.ALIGN_CENTER
        val headCellTitle2 = PdfPCell(paragraph2)
        headCellTitle2.horizontalAlignment = Element.ALIGN_LEFT
        headCellTitle2.verticalAlignment = Element.ALIGN_CENTER
        headCellTitle2.setPadding(5F)
        headCellTitle2.borderWidthRight=0F
        headCellTitle2.borderWidthTop=0F
        table.addCell(headCellTitle2)

        table.widthPercentage = 90F
        table.totalWidth = 205F
        table.isLockedWidth = true
        totalHeight += table.totalHeight
        return table

    }

    fun barcodeTable(aCode: String?, font: BaseFont, pdfWriter: PdfWriter): PdfPTable {
        val table = PdfPTable(1)
        table.runDirection = PdfWriter.RUN_DIRECTION_RTL
        table.widthPercentage = 90F
        val cb = pdfWriter.directContent
        val code128 = Barcode128()
        code128.font = font
        code128.size = 6F
        code128.barHeight = 12F
        code128.x = 1f
        code128.code = aCode
        code128.codeType = Barcode128.CODE128
        val code128Image = code128.createImageWithBarcode(cb, null, null)
        val headCell = PdfPCell(code128Image)
        headCell.horizontalAlignment = Element.ALIGN_CENTER
        headCell.verticalAlignment = Element.ALIGN_CENTER
        headCell.setPadding(5F)
        headCell.border = PdfPCell.NO_BORDER
        table.addCell(headCell)
        table.widthPercentage = 70f
        table.totalWidth = 205F
        table.isLockedWidth = true
        totalHeight += table.totalHeight
        return table

    }


}


