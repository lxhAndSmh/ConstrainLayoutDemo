package com.botpy.constrainlayoutexample.kotlin

/**
 * @author liuxuhui
 * @date 2019-07-26
 */
fun main(args: Array<String>) {
    var num = 2
    val price: Double = 10.5
    val name: String = "大米"

    println("单价：$price")
    println("数量：$num")
    println("产品：$name 总计：${num * price}")

    // in 关键字
    var x = 3
    var y = 10
    //判断一个对象是否在一个区间内
    if(x in 1 until y - 1) {
        println("OK")
    }

    var arrays: Array<Int> = arrayOf(1,2)
    //遍历集合（类似于 Java 中的 for(int num : arrays)）,如果 x 不存在 arrays 中则输出 OUT
    if(x !in arrays) println("OUT")

    // when 表达式
    cases(1)
    cases("hello")
    cases(20)
    cases(2)
    cases("world")

    //智能类型推测
    println(getStringLength("This is String"))
    println(getStringLength(2))

    //控制检测
    println(arrays?.size)

    //函数
    println(say("Hello"))
    println(say1("World"))
    println(say2("hey"))
    println(say3())

    //变长参数
    println(hasEmptyStr("I", "love", "", "you"))
}
fun cases(obj: Any) {
    when(obj) {
        1 -> println("我是1")
        "hello" -> println("这个是字符串hello")
        is Long -> println("这是一个 Long 类型数据")
        !is String -> println("这不是 String 类型的数据")
        else -> println("else 类似于 Java 中的 default")
    }
}

fun getStringLength(obj: Any): Int? {
    //做过类型判断以后， obj 会被系统自动转换为 String 类型
    if(obj is String) return obj.length

    //!is ,表示取反
    if(obj !is String){

    }

    //代码块外部的 obj 仍然是 Any 类型的引用
    return null
}

//函数
fun say(str : String): String {
    return str
}
//这种简单的函数可以简写为
fun say1(str: String): String = str
//事实上只要编译器可以推断的类型，你甚至连返回类型都可以不写
fun say2(str: String) = str
//可以使用默认参数
fun say3(str: String = "默认值") = str

//变长参数
fun hasEmptyStr(vararg arrayStr: String?): Boolean{
    //遍历集合
    for(str in arrayStr) {
        //？表示非空判断
        str?: return true
    }
    return false
}

object HelloKotlin {
    @JvmStatic fun isEmpty(str: String?): Boolean {
        return "" == str;
    }
}
