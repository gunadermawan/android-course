package com.gun.course

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnMoveActivity: Button
    private lateinit var btnDial: Button
    private lateinit var btnMoveData: Button

    companion object {
        private const val EXTRA_NAME = "extra_name"
        const val EXTRA_AGE = "extra_age"
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
        btnMoveActivity = findViewById(R.id.btn_move_activity)
        btnDial = findViewById(R.id.btn_dial)
        btnMoveData = findViewById(R.id.btn_move_data)
        btnMoveActivity.setOnClickListener(this)
        btnDial.setOnClickListener(this)
        btnMoveData.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_move_activity -> {
                val intent = Intent(this@MainActivity, MoveActivity::class.java)
                startActivity(intent)
            }

            R.id.btn_dial -> {
                val phone = "08123456789"
                val moveIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
                startActivity(moveIntent)
            }

            R.id.btn_move_data -> {
                val moveIntentData = Intent(this@MainActivity, MoveDataActivity::class.java)
                moveIntentData.putExtra(MoveDataActivity.EXTRA_NAME, "Samuel")
                moveIntentData.putExtra(MoveDataActivity.EXTRA_AGE, 19)
                startActivity(moveIntentData)
            }
        }
    }
}