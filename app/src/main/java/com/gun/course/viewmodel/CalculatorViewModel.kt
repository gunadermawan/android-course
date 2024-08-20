package com.gun.course.viewmodel

import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {
    fun add(a: Int, b: Int): Int {
        return a + b
    }

    fun subtract(a: Int, b: Int): Int {
        return a - b
    }
}