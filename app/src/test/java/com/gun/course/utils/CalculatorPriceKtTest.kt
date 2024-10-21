package com.gun.course.utils

import org.junit.Assert.*
import org.junit.Test

class CalculatorPriceKtTest {
    @Test
    fun calculateTotalPrice_isCorrect() {
        val items = listOf(CartItem("Apple", 2, 3000), CartItem("Banana", 1, 2000))
        val expectedTotal = 2 * 3000 + 1 * 2000 - 200
        assertEquals(expectedTotal, calculateTotalPrice(items))
    }
}