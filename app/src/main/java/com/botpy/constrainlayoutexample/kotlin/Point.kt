package com.botpy.constrainlayoutexample.kotlin

/**
 * @author liuxuhui
 * @date 2019-09-18
 */
class Point(var centerX: Int, var centerY: Int, var index: Int) {

    val STATUS_NORMAL = 1
    private val STATUS_PRESSED = 2
    private val STATUS_ERROR = 3

    var status = STATUS_NORMAL
}