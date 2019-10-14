package com.botpy.constrainlayoutexample.kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.botpy.constrainlayoutexample.R
import kotlinx.android.synthetic.main.activity_kotlin.*
/**
 * @author liuxuhui 
 * @date 2019-07-24
 */
class KotlinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)

        textView.text = getString(R.string.hello_world)

        editText.setText("${HelloKotlin.isEmpty("非空")}")
    }

    fun AppCompatActivity.showToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, text, duration)
    }
}
