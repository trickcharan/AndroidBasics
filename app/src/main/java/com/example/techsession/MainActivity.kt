package com.example.techsession

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private lateinit var textview:TextView
    private lateinit var imageView: ImageView
    private lateinit var editText: EditText
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textview = findViewById(R.id.tv_sample)
        imageView = findViewById(R.id.iv_sample)
        button = findViewById(R.id.btn_sample)
        editText = findViewById(R.id.et_sample)

        button.setOnClickListener {
            Toast.makeText(this,R.string.message,Toast.LENGTH_SHORT).show()
            //showPopup()
        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(editable: Editable?) {
                Log.d(this.javaClass.simpleName,editable.toString())
                //button.text = editable.toString()
                //textview.text = editable.toString()
            }
        })

    }

    private fun showPopup(){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle(R.string.clicked)
            setMessage(R.string.message)
            setPositiveButton(R.string.ok
            ) { p0, p1 -> }
            setCancelable(false)
            show()
        }
//        alertDialog.setTitle(R.string.clicked)
//        alertDialog.setMessage(R.string.message)
//        alertDialog.setPositiveButton(R.string.ok
//        ) { p0, p1 -> }
//        alertDialog.setCancelable(false)
//        alertDialog.show()
    }

    private fun openGameActivity(){
        startActivity(Intent(this,GameActivity::class.java))
    }
}