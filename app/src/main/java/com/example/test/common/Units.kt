package com.example.test.common

import java.text.DecimalFormat

fun stringToNumber(price: String): String {
    var size=price.length-1
    var result=price
    if (price.contains('.')){
        for (i in size downTo 0){
            if (result[i]=='0')
                result= result.substring(0,i)
            else if (result[i]=='.'){
                result= result.substring(0,i)
                return englishNumberToPersian(result)
            }
            else
                return englishNumberToPersian(result)
        }
    }

    return englishNumberToPersian(result)
}

fun englishNumberToPersian(number:String):String{
    var result=""
    val size=number.length-1
    for (i in 0..size){
        when(number[i]){
            '0'-> result+='۰'
            '1'->result+='۱'
            '2'->result+='۲'
            '3'->result+='۳'
            '4'->result+='۴'
            '5'->result+='۵'
            '6'->result+='۶'
            '7'->result+='۷'
            '8'->result+='۸'
            '9'->result+='۹'
            else -> result+=number[i]

        }
    }

    return result
}


fun String.beautifyPrice( isNegative: Boolean = false): String {
    val formatter =  DecimalFormat("#,##0")
    val handle=StringBuilder(formatter.format(this.toDouble()))
    if (isNegative)handle.append(")").insert(0,"(")
    return handle.toString()
}
