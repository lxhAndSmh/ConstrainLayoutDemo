package com.botpy.constrainlayoutexample.kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.botpy.constrainlayoutexample.R
import com.botpy.constrainlayoutexample.view.LockPatternView
import kotlinx.android.synthetic.main.activity_kotlin.*
/**
 * @author liuxuhui 
 * @date 2019-07-24
 */
class KotlinActivity : AppCompatActivity(), LockPatternView.LockListener {
    override fun onSuccess() {
        Toast.makeText(this, "密码正确", Toast.LENGTH_SHORT).show()
       finish()
    }

    override fun onError() {
        Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)

        textView.text = "密码：01246"

        editText.setText("${HelloKotlin.isEmpty("非空")}")

        lockView.setPassword("01246")
        lockView.setLockListener(this)
    }

    fun AppCompatActivity.showToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, text, duration)
    }
}
