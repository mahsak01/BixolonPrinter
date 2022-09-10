import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.test.R
import com.example.test.common.beautifyPrice
import com.example.test.common.englishNumberToPersian
import com.example.test.common.stringToNumber
import com.example.test.data.model.InvoiceSaleItem
import com.itextpdf.text.*
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfWriter
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime

private const val FONT = "assets/iran_sans_mobile.ttf"

class InvoiceSale {

    @RequiresApi(Build.VERSION_CODES.O)
    fun createTable(context: Context,fileName:String,data:List<InvoiceSaleItem>):File {


        val fontBig = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, 7F, Font.NORMAL)
        val fontSmall = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, 5F, Font.NORMAL)
        val fontYekan = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED)

        val items = ArrayList<String>()
        items.add("۱۰:۵۵:۴۹")
        items.add(englishNumberToPersian(data[0].invoiceDateSlash!!))
        items.add("شماره فاکتور")
        items.add(englishNumberToPersian(data[0].invoiceNo!!))
        items.add("زمان تسویه")
        items.add(englishNumberToPersian("0".beautifyPrice()))
        items.add("نوع پرداخت")
        items.add("نقد")
        items.add("نام مشتری")
        items.add(data[0].customerName!!)

        val tableGenerator = TableGenerator()
        val itemsDocument1 = ArrayList<Element>()


//        itemsDocument1.add(tableGenerator.imageCenter("assets/logokian.png"))

        itemsDocument1.add(tableGenerator.textCenter("متن1", "", fontBig))
        itemsDocument1.add(tableGenerator.textCenter("متن2", "", fontBig))

        var arrayHeaderTable = FloatArray(2)
        arrayHeaderTable[0] = 150F
        arrayHeaderTable[1] = 50F
        val headTable = tableGenerator.headTable(
            "صورتحساب فروش کالا یا خدمات",
            items,
            fontBig,
            arrayHeaderTable
        )
        itemsDocument1.add(headTable)


        itemsDocument1.add(tableGenerator.textRightWithBorder("آدرس:", data[0].address!!, fontBig))

        val itemsT2 = ArrayList<String>()
        itemsT2.add("کالا یا خدمات")
        itemsT2.add("تعداد")
        itemsT2.add("فـــی")
        itemsT2.add("تخفیف")
        itemsT2.add("فـی پـس از تخفیف")
        itemsT2.add("مبلغ کل")
        for ( item in data){
            itemsT2.add(item.itemDs!!)
            itemsT2.add(stringToNumber(item.itemValue!!.toString()))
            itemsT2.add(stringToNumber(item.unitPrice!!.toString().beautifyPrice()))
            itemsT2.add("۰%")
            itemsT2.add(englishNumberToPersian("10000".beautifyPrice()))
            itemsT2.add(stringToNumber(item.totalPrice.toString().beautifyPrice()))
        }
        var array = FloatArray(6)
        array[0] = 20F
        array[1] = 20F
        array[2] = 20F
        array[3] = 20F
        array[4] = 17F
        array[5] = 23F
        val productTable =
            tableGenerator.tableProduct("مشخصات کالا یا خدمات مورد معامله", itemsT2, fontBig, array)
        itemsDocument1.add(productTable)


        val items3 = ArrayList<String>()
        items3.add("جمع کل به ريال :")
        items3.add(stringToNumber(data[0].sumTotalPrice.toString().beautifyPrice()))
        items3.add("مبلغ پس از کسر تخفيفات به ريال :")
        items3.add(stringToNumber(data[0].sumUnTaxPrice.toString().beautifyPrice()))
        items3.add("بدهي پيشين به ريال :")
        items3.add("۰")
        items3.add("جمع کل مانده حساب به ريال :")
        items3.add(stringToNumber(data[0].sumFinalPrice.toString().beautifyPrice()))
        val footerTable = tableGenerator.footerTable(items3, fontBig)
        itemsDocument1.add(footerTable)


        val itemsDocument2 = ArrayList<Element>()



        itemsDocument2.add(
            tableGenerator.textRightWithOutBorder(
                "نام ویزیتور :",
                " مرجانه مولايي",
                fontBig
            )
        )
        itemsDocument2.add(tableGenerator.textCenter("متن1", "", fontBig))
        itemsDocument2.add(
            tableGenerator.textRightWithOutBorder(
                "شاهرود شهرک صنعتي خيابان پنجم کوي دهم",
                "",
                fontBig
            )
        )
        itemsDocument2.add(tableGenerator.textCenter("متن2", "", fontBig))

        itemsDocument2.add(tableGenerator.textCenter("", "", fontBig))

        itemsDocument2.add(
            tableGenerator.textRightWithOutBorder(
                "علامت ستاره بمنزله کالای اشانتیون می باشد",
                "",
                fontSmall
            )
        )
        itemsDocument2.add(
            tableGenerator.textRightWithOutBorder(
                "حروف اختصاری (ن ک)بمنزله تخفیف نقدی روی کل ردیف کالا می باشد",
                "",
                fontSmall
            )
        )
        itemsDocument2.add(
            tableGenerator.textRightWithOutBorder(
                "حروف اختصاری (ن ج)بمنزله تخفیف نقدی بصورت جز روی مبلغ کالا می باشد",
                "",
                fontSmall
            )
        )

        itemsDocument2.add(tableGenerator.textCenter("", "", fontBig))
        itemsDocument2.add(
            tableGenerator.textCenterWithBackground(
                "نرم افزار پخش مویرگی تیانا www.nak-it.com",
                "",
                fontSmall
            )
        )
        itemsDocument2.add(tableGenerator.textCenter("", "", fontBig))

        tableGenerator.totalHeight += 40

        val document = Document(Rectangle(226F, tableGenerator.totalHeight), 0F, 0F, 0F, 0F)
        val folder = File(context.getExternalFilesDir(null)?.path + "/pdfs")
        val file = File(context.getExternalFilesDir(null)?.path + "/pdfs", fileName)
        if (!folder.exists())
            folder.mkdirs()
        if (!file.exists()) {
            file.createNewFile()
        }
        val pdfWriter: PdfWriter =
            PdfWriter.getInstance(document, FileOutputStream(file))
        pdfWriter.runDirection = PdfWriter.RUN_DIRECTION_RTL
        document.open()

        itemsDocument1.forEach {
            document.add(it)
        }
        document.add(tableGenerator.barcodeTable(data[0].barcodeValue, fontYekan, pdfWriter))

        itemsDocument2.forEach {
            document.add(it)
        }
        document.close()

        return file
    }
}