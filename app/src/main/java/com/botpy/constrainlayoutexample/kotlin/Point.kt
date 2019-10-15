package com.botpy.constrainlayoutexample.kotlin

/**
 * @author liuxuhui
 * @date 2019-09-18
 */
class Point(var centerX: Int, var centerY: Int, var index: Int) {
    fun setStatusPressed() {
        status = POINT_STATUS_PRESSED
    }

    fun setStatusError() {
        status = POINT_STATUS_ERROR
    }

    fun setStatusNormal() {
        status = POINT_STATUS_NORMAL
    }

    private val POINT_STATUS_NORMAL = 1
    private val POINT_STATUS_PRESSED = 2
    private val POINT_STATUS_ERROR = 3

    var status = POINT_STATUS_NORMAL

}