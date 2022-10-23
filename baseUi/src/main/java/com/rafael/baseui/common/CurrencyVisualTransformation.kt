package com.rafael.baseui.common

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.lang.Integer.max
import java.text.DecimalFormat

class CurrencyVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val symbols = DecimalFormat().decimalFormatSymbols
        val thousandsSeparator = symbols.groupingSeparator
        val decimalSeparator = symbols.decimalSeparator

        val inputText = text.text.filter { it.isDigit() }

        val intPart = if (inputText.length > 2) {
            inputText.subSequence(0, inputText.length - 2)
        } else {
            "0"
        }

        var fractionPart = if (inputText.length >= 2) {
            inputText.subSequence(inputText.length - 2, inputText.length)
        } else {
            inputText.padStart(2, '0')
        }

        val thousandsReplacementPattern = Regex("\\B(?=(?:\\d{3})+(?!\\d))")
        val formattedIntWithThousandsSeparator =
            intPart.replace(
                thousandsReplacementPattern,
                thousandsSeparator.toString()
            )

        val newText = AnnotatedString(
            formattedIntWithThousandsSeparator + decimalSeparator + fractionPart,
            text.spanStyles,
            text.paragraphStyles
        )

        return TransformedText(
            text = newText,
            offsetMapping = ThousandSeparatorOffsetMapping(intPart.length)
        )
    }
}

private class ThousandSeparatorOffsetMapping(
    val originalIntegerLength: Int
) : OffsetMapping {

    override fun originalToTransformed(offset: Int): Int =
        when (offset) {
            0, 1, 2 -> 4
            else -> offset + 1 + calculateThousandsSeparatorCount(originalIntegerLength)
        }

    override fun transformedToOriginal(offset: Int): Int =
        originalIntegerLength +
                calculateThousandsSeparatorCount(originalIntegerLength) +
                2

    private fun calculateThousandsSeparatorCount(
        intDigitCount: Int
    ) = max((intDigitCount - 1) / 3, 0)
}