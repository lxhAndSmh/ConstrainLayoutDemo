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

        editText.addTextChangedListener(object : TextWatcher {
            //不需要重写的方法
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            //不需要重写的方法
            override fun afterTextChanged(s: Editable?) = Unit
        })

        editText.setText("$HelloKotlin.isEmpty(\"非空\")")
    }

    fun AppCompatActivity.showToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, text, duration)
    }
}
