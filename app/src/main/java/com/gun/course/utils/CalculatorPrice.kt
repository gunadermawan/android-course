package com.gun.course.utils

data class CartItem(
    val name: String,
    val quantity: Int,
    val pricePerItem: Int,
    val discount: Int = 100
)

fun calculateTotalPrice(items: List<CartItem>): Int {
    return items.sumOf { (it.quantity * it.pricePerItem) - it.discount }
}