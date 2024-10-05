package mz.co.macave.mundodoscarros.utils

import java.text.NumberFormat
import java.util.Locale

class Converter {

    companion object {

        fun toOtherCurrency(amount: String, rate: Double): String {
            val c = NumberFormat.getNumberInstance()
            c.maximumFractionDigits = 2
            c.minimumFractionDigits = 2
            return c.format(getAmountInRS(amount) * rate)
        }

        private fun getAmountInRS(amountInRS: String): Double {
            return  amountInRS.substringAfter("R$ ")         //Removing R$ from the amount
                .replace('.',' ')                   //Replacing the DOT by a whitespace
                .trim()                                             //Removing the leading and trailing whitespaces
                .replace(',','.')                   //Replacing the COMMA by a DOT
                .replace(" ","")                   //Removing the whitespace in the middle
                .toDouble()
        }
    }
}