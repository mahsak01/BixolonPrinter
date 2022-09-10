import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.test.data.model.InvoiceSaleItem
import com.itextpdf.text.*
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfWriter
import java.io.File
import java.io.FileOutputStream

private const val FONT = "assets/iran_sans_mobile.ttf"

private const val FONTBold = "assets/IRANSansMobile_Bold.ttf"


class InvoiceDeliveryReceipt {

    @RequiresApi(Build.VERSION_CODES.O)
    fun createTable(context: Context, fileName:String): File {
        val fontBig = FontFactory.getFont(FONTBold, BaseFont.IDENTITY_H, 8F, Font.NORMAL)
        val fontSmall = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, 6F, Font.NORMAL)
        val tableGenerator = TableGenerator()

        val itemsDocument1 = ArrayList<Element>()


        itemsDocument1.add(tableGenerator.textCenter("", "", fontBig))


        itemsDocument1.add(tableGenerator.headerImageAndTitleLeft("تیانا\n\nرسید تحویل فاکتور", "assets/logo.png", fontBig))
        itemsDocument1.add(tableGenerator.textTableWith2Column("شماره سند توزیع :54545151", "فاکتور بصورت ناقص تحویل شد", fontSmall))
        itemsDocument1.add(tableGenerator.textTableWith2Column("مامور توزیع: حسن جعفری", "تاریخ سند :05/10/1401", fontSmall))
        itemsDocument1.add(tableGenerator.textTableWith1Column("راننده :", fontSmall))
        itemsDocument1.add(tableGenerator.textTableWith1Column("ناوگان حمل :", fontSmall))
        itemsDocument1.add(tableGenerator.textTableWith2Column("شماره فاکتور :51551542", "شماره دوم :3919", fontSmall))
        itemsDocument1.add(tableGenerator.textTableWith1Column("نام مشتری : محمد حسین احمدی / مرکزی", fontSmall))
        itemsDocument1.add(tableGenerator.textTableWith1Column("تحویل گیرنده :", fontSmall))
        itemsDocument1.add(tableGenerator.textTableWith2Column("تاریخ تحویل :05/10/1401", "ساعت تحویل : 12:00:00", fontSmall))
        itemsDocument1.add(tableGenerator.textTableWith1Column("آدرس تحویل فاکتور : -", fontSmall))
        itemsDocument1.add(tableGenerator.textTableWith1Column("توضیحات :", fontSmall))
        itemsDocument1.add(tableGenerator.textTableWith1Column(" ", fontSmall))

        var array = FloatArray(4)
        array[0] = 20F
        array[1] = 20F
        array[2] = 30F
        array[3] = 40F
        val itemsT = ArrayList<String>()
        itemsT.add("نام کالا")
        itemsT.add("دلیل")
        itemsT.add("تعداد سفارش")
        itemsT.add("تحویل نشده")
        itemsT.add("نقل خلال")
        itemsT.add("بدون دلیل")
        itemsT.add("3")
        itemsT.add("1")
        itemsT.add("نقل خلال")
        itemsT.add("بدون دلیل")
        itemsT.add("3")
        itemsT.add("1")
        itemsT.add("نقل خلال")
        itemsT.add("بدون دلیل")
        itemsT.add("3")
        itemsT.add("1")
        itemsT.add("نقل خلال")
        itemsT.add("بدون دلیل")
        itemsT.add("3")
        itemsT.add("1")

        itemsDocument1.add(tableGenerator.tableProduct(null, itemsT, fontSmall, array))
        itemsDocument1.add(tableGenerator.textTableWith1Column(" ", fontSmall))
        itemsDocument1.add(tableGenerator.headerImageAndTitleRight("امضا تحویل گیرنده", "assets/sign.png", fontSmall))
        itemsDocument1.add(tableGenerator.footerTableWithText("گزارش گیرنده: مدیرسیستم","نرم افزار جامع تیانا www.nak-it.com", fontSmall))
        itemsDocument1.add(tableGenerator.textCenter("", "", fontBig))

        val document = Document(Rectangle(226F, tableGenerator.totalHeight), 0F, 0F, 0F, 0F)
        val file = File(context.getExternalFilesDir(null)?.path + "/pdfs", fileName)
        if (!file.exists()) {
            file.createNewFile()
        }
        val pdfWriter: PdfWriter =
            PdfWriter.getInstance(document, FileOutputStream(file))
        pdfWriter.runDirection = PdfWriter.RUN_DIRECTION_RTL;
        document.open()

        for (item in itemsDocument1)
            document.add(item)
        document.close()

        return file

    }
}