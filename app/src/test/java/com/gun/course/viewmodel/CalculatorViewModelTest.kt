package com.gun.course.viewmodel

import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class CalculatorViewModelTest {

    private lateinit var viewModel: CalculatorViewModel

    @Before
    fun setUp() {
        viewModel = CalculatorViewModel()
    }

    @Test
    fun add() {
        val result = viewModel.add(4, 6)
        assertEquals(10, result)
    }

    @Test
    fun subtract() {
        val result = viewModel.subtract(5, 1)
        assertEquals(4, result)
    }
}