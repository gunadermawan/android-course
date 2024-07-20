package com.gun.course

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var edtWidth: EditText
    private lateinit var edtLength: EditText
    private lateinit var edtHigh: EditText
    private lateinit var btnCalculate: Button
    private lateinit var tvResult: TextView
    private lateinit var btnFragment: Button

    companion object {
        private const val STATE_RESULT = "state_result"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(15, systemBars.top, 15, systemBars.bottom)
            insets
        }
        edtWidth = findViewById(R.id.edt_width)
        edtLength = findViewById(R.id.edt_length)
        edtHigh = findViewById(R.id.edt_height)
        btnCalculate = findViewById(R.id.btn_calculate)
        tvResult = findViewById(R.id.tv_result)
        btnCalculate.setOnClickListener(this)
        btnFragment = findViewById(R.id.btn_fragment)
        btnFragment.setOnClickListener(this)
        if (savedInstanceState != null) {
            val result = savedInstanceState.getString(STATE_RESULT)
            tvResult.text = result
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_RESULT, tvResult.text.toString())
    }


    fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_container, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun onClick(view: View?) {
        if (view?.id == R.id.btn_calculate) {
            val inputLength = edtLength.text.toString().trim()
            val inputWidth = edtWidth.text.toString().trim()
            val inputHigh = edtHigh.text.toString().trim()
            var isEmptyFields = false
            if (inputLength.isEmpty()) {
                isEmptyFields = true
                edtLength.error = "Filed tidak boleh kosong"
            }
            if (inputWidth.isEmpty()) {
                isEmptyFields = true
                edtLength.error = "Filed tidak boleh kosong"
            }
            if (inputHigh.isEmpty()) {
                isEmptyFields = true
                edtLength.error = "Filed tidak boleh kosong"
            }
            if (!isEmptyFields) {
                val volume = inputLength.toDouble() * inputWidth.toDouble() * inputHigh.toDouble()
                tvResult.text = volume.toString()
            }
        }
        if (view?.id == R.id.btn_fragment) {
            replaceFragment(MainFragment())
        }
    }
}