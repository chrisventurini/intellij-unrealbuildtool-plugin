package com.visionsof.intellijunrealbuildtoolplugin.ui.textformatting

import net.objecthunter.exp4j.ExpressionBuilder
import java.text.NumberFormat
import javax.swing.text.NumberFormatter

class ExpressionNumberFormatter(numberFormat: NumberFormat) : NumberFormatter(numberFormat) {
    private var equationRegex: Regex = Regex("""^(\d+)([-+/^%*]\d+(\.\d+)?)+""")

    override fun stringToValue(text: String?): Any {
        lateinit var parsedValue : Any;

        if(text != null) {
            if (equationRegex.matches(text)) {
                val exp = ExpressionBuilder(text).build()
                if (exp.validate().isValid) {
                    parsedValue = exp.evaluate()
                }
            } else if (format != null) {
                parsedValue = format.parseObject(text)
            }

            if (valueClass != null && parsedValue is Number) {
                when (valueClass) {
                    Int::class.java -> {
                        return Integer.valueOf(parsedValue.toInt())
                    }
                    Long::class.java -> {
                        return java.lang.Long.valueOf(parsedValue.toLong())
                    }
                    Float::class.java -> {
                        return java.lang.Float.valueOf(parsedValue.toFloat())
                    }
                    Double::class.java -> {
                        return java.lang.Double.valueOf(parsedValue.toDouble())
                    }
                    Byte::class.java -> {
                        return java.lang.Byte.valueOf(parsedValue.toByte())
                    }
                    Short::class.java -> {
                        return parsedValue.toShort()
                    }
                }
            }
        }

        return ""
    }
}
