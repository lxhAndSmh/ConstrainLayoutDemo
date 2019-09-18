package com.botpy.constrainlayoutexample.kotlin

/**
 * @author liuxuhui
 * @date 2019-09-18
 */
class Point(var centerX: Int, var centerY: Int) {

    var isPressed = false

    fun setPressed() {
        isPressed = true
    }
}