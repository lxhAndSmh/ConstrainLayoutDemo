### Kotlin 的使用

#### 1 入门

##### 1.1 Kotlin 的优势
- 相比 Java，Kotlin 有着更好的语法结构，安全性和开发工具支持。
- Kotlin 中没有基础类型，数组是定长的，泛型是安全的，即便运行时也是安全的。
- Kotlin 支持闭包，还可通过内联进行优化
- 避免了 Java 语言编程中的一些难题。比如：在 Kotlin 中类型系统控制了空指针的引用，可以有效避免 Java 中常见的 NullPointException。
- Java 和 kotlin 之间的互操作性：Kotlin 可以调用 Java，反之亦可。

##### 1.2 Kotlin 的简单使用
- 函数的声明用 `fun`表示。 fun getName()
- 变量的声明用`var`，常量的声明用`val`，变量名在前，变量类型在后，中间用冒号隔开。（var name: String）
- Kotlin 程序非常简洁，连分号也不需要写
- Kotlin 创建对象的时候不需要`new`关键字，而是像调用普通方法一样直接调用构造方法就可以了。

##### 1.3 编码风格

同 Java 类似，使用驼峰命名法。
- 类名首字母大写，每个单词的首字母大写
- 方法和属性变量首字母小写
- 采用四个空格缩进
- 官方文档中建议在 Kotlin 语言中不要给属性前面加前缀，例如通常我们习惯加上一个小写`m`或者下划线等
- 冒号，在分割两个类型时，应该在左右都有空格，在实例和类型之间，应该紧靠实例变量
```
interface Foo<out T : Any> : Bar {
    fun foo(a: Int): T
}
```
- Lambdas 表达式，如果是简单的只用一行就可以表示的 lambdas，应当遵循在大括号的两侧、箭头的两侧、参数的两侧都是用空格隔开，例如：
```
list.filter { it > 10 }.map { element -> element * 2 }
```

#### 2 基本语法

##### 2.1变量
```
fun main(args: Array<String>) {
    var num = 2
    val price: Double = 10.5
    val name: String = "大米"

    println("单价：$price")
    println("数量：$num")
    println("产品：$name 总计：${num * price}")
}
```
可以看出，Kotlin 语言声明一个变量使用关键字`var`，声明一个常量使用`val`，声明时 Kotlin 语言可以自动推测出字段类型，例如上面代码中
的`var num = 2`会被认为是`Int`类型，如果你希望它是一个`Double`类型，则需要声明类型，例如`var num: Double = 2`

##### 2.2 语句

###### 2.2.1 in 关键字的使用

判断一个对象是否在某一个区间内，可以使用 in 关键字
```
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
```

###### 2.2.2 when 表达式

类似于 Java 中的 switch，但是 Kotlin 更加智能，可以自动判断参数的类型并转换为相应的匹配值。
```
// when 表达式
cases(1)
cases("hello")
cases(20)
cases(2)
cases("world")

fun cases(obj: Any) {
    when(obj) {
        1 -> println("我是1")
        "hello" -> println("这个是字符串hello")
        is Long -> println("这是一个 Long 类型数据")
        !is String -> println("这不是 String 类型的数据")
        else -> println("else 类似于 Java 中的 default")
    }
}
```

###### 2.2.3 智能类型推测

判断一个对象是否为一个类的实例，可以使用 is 关键字；与 Java 中的 `instanceof` 关键字类似，但在 Kotlin 中如果已经确定了一个对象的类型，可以在接下来的代码中直接作为这个确定类型使用。
```
un getStringLength(obj: Any): Int? {
    //做过类型判断以后， obj 会被系统自动转换为 String 类型
    if(obj is String) return obj.length

    //!is ,表示取反
    if(obj !is String){

    }

    //代码块外部的 obj 仍然是 Any 类型的引用
    return null
}
```

###### 2.2.4 空值检测

Kotlin 是空指针安全的，也就意味着不会再看到那恼人的空指针异常。
例如`println(files?.size)`,只会在`files`不为空时执行。
你也可以这样写
```
//当data不为空的时候，执行语句块
data?.let{
    //...
}

//相反的，以下代码当data为空时才会执行
data?:let{
    //...
}
```

###### 2.2.5 相等判断   

Kotlin 中存在两种相等判断：
- 结构相等（等价于 Java 的 `equals()` 判断）
- 引用相等（两个引用指向同一个对象，等价于 Java 中的`==`）

######   2.2.5.1 结构相等
Kotlin 中结构相等使用`==`操作（以及它的相反操作`!=`）来判断
######   2.2.5.2 引用相等
Kotlin 中引用相等使用`===`操作（以及它的相反操作`!==`）来判断。当且仅当，`a` 与 `b` 指向同一个对象时，`a ===b` 结果为true；但对于基本类型
那些值（比如 `Int`）,`===` 判断等价于 `==` 判断。
```
var a = "hello"
var b = "hello"

a == b(结构相等) 的结果为 true， a === b(引用相等) 的结果为 true

Kotlin 中的基本类型：数值、字符、布尔值、字符串
数值类型：Double、Float、Long、Int、Short、Byte
字符：Char
布尔值：Boolean
字符串：String
```

##### 2.3 函数

###### 2.3.1 函数的声明

函数使用关键字 `fun` 声明，如下代码创建了一个名为`say()`的函数，它接受一个`String`类型的参数，并返回一个`String`类型的值
```
/函数
fun say(str : String): String {
    return str
}
//这种简单的函数可以简写为
fun say1(str: String): String = str
//事实上只要编译器可以推断的类型，你甚至连返回类型都可以不写
fun say2(str: String) = str
```

###### 2.3.2 函数的默认值

可以使用默认参数来实现重载类似的功能
```
//可以使用默认参数
fun say3(str: String = "默认值") = str
```
这个时候可以调用`say3()`，来得到默认的字符串`"默认值"`，也可以自己传入参数`say3("world")`来得到传入的参数值.

###### 2.3.3 变参函数

同 Java 的变长参数一样，Kotlin 也支持变长参数，在 Kotlin 中，使用关键字 `vararg` 来表示变长参数。
```
//变长参数
fun hasEmptyStr(vararg arrayStr: String?): Boolean{
    //遍历集合
    for(str in arrayStr) {
        //？表示非空判断
        str?: return true
    }
    return false
}
```

###### 2.3.4 扩展函数

可以给父类添加一个方法，这个方法将可以在所有子类中使用。
例如，我们在 Android 开发中，常常使用这样的扩展函数:
```
fun AppCompatActivity.showToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
       Toast.makeText(this, text, duration)
   }
```
这样，就可以在每一个`Activity`中直接使用 toast() 函数了。

#### 3 Kotlin 和 Java 混编

##### 3.1 在 Kotlin 中调用 Java 代码

###### 3.1.1 返回 void 的方法

如果一个 Java 方法返回 void，对应的在 Kotlin 代码中它将返回 Unit，在 Kotlin 中也可以省略这个返回类型。

Unit 表示的是一个值的类型，这种类型类似于 Java 中的 `void` 类型。

如果一个函数是空函数，比如 Android 开发中的 TextWatch 接口，通常只会用到一个方法，但必须把所有方法都重写一遍，用`Unit`可以简写：
```
editText.addTextChangedListener(object : TextWatcher {
           //不需要重写的方法
           override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

           override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
               TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
           }

           //不需要重写的方法
           override fun afterTextChanged(s: Editable?) = Unit
       })
```

###### 3.1.2 与 Kotlin 关键字冲突的处理

Java 有 static 关键字，在 Kotlin 中没有这个关键字，你需要使用`@JvmStatic`替代这个关键字。
同样，在 Kotlin 中也有很多关键字是 Java 中没有的。例如`in`、`is`、`data`等。如果 Java 中使用了这些关键字，Kotlin 的关键字需要加上反引号(``)转义来避免冲突(不建议使用关键字命名方法)。例如：
```
// Java 代码中有个方法叫 is()
public void is(){
    //...
}

// 转换为 Kotlin 代码需要加反引号转义
fun `is`() {
   //...
}
```

##### 3.2 在 Java 中调用 Kotlin 代码

###### 3.2.1 static 方法
在 Kotlin 中没有`static`关键字，那么如果在 Java 代码中想要通过类名调用一个 Kotlin 类的方法，你需要给这个方法加入 `@JvmStatic`注解(这个注解只在 jvm 平台有用)。否则你必须通过对象调用这个方法。
```
object HelloKotlin {
    @JvmStatic fun isEmpty(str: String?): Boolean {
        return "" == str;
    }
}
```
在 Kotlin 中，经常会看到这样一种写法。
```
class HelloKotlin {
    companion object {
      fun isEmpty(str: String?): Boolean {
          return "" == str;
      }
    }
}
```
companion object 表示外部类的一个伴生对象，你可以把他理解为外部类自动创建了一个对象做为自己的`field`。
Java 在调用时，可以这样写`HelloKotlin.Companion.isEmpty();`也可以省略中间的 Companion，写作`HelloKotlin.isEmpty();`

###### 3.2.1 包级别函数

与 Java 不同，Kotlin 允许函数独立存在，而不必依赖于某个类，这类函数我们称之为`包级别函数`。
为了兼容 Java，Kotlin 默认会将所有的包级别函数放在一个自动生成的叫`ExampleKt`的类中，在 Java 中想要调用包级别函数时，需要通过这个类来调用。
当然，类名也是可以自定义的，你只需要通过注解`@file:JvmName("Example")`即可将当前文件中的所有包级别函数放到一个自动生成的名为 Example 的类中。

#### 4 Kotlin 类的特性

##### 4.1 构造函数

- Kotlin 的构造函数可以写在类头中，跟在类名后面，如果有注解需要加上关键字`constructor`。这种写法声明的构造函数，我们称之为主构造函数。
```
class Person(private val name: String) {
  fun sayHello() {
    println("hello $name")
  }
}
```
- 在构造函数中声明的参数，默认属于类的共有字段，可以直接使用，如果不希望别的类访问到这个变量，可以用`private`修饰。
- 在主构造函数中不能有任何代码的实现，如果有额外的代码需要在构造方法中执行，需要放到`init`代码块中执行。
```
class Person(private var name: String) {

  init {
    name = "默认值"
  }

  fun sayHello() {
    println("hello $name")
  }
}
```
- 如果一个非抽象类没有声明任何（主或次）构造函数，它会有一个生成的不带参数的主构造函数。

##### 4.2 次级构造函数

一个类可以有多个构造函数，只有主构造函数可以写在类头中，其他的次级构造函数需要写在类体中。
```
class Person(private var name: String) {
  private var des: Strng？= null

  init {
    name = "默认值"
  }

  constructor(name: String, des: String): this(name) {
    this.des = des;
  }

  fun sayHello() {
    println("hello $name")
  }
}
```
这里次级函数调用了主构造函数，完成 name 的赋值。由于次级构造函数不能直接将参数转换为字段，所以需要声明一个 des 字段，并为其赋值。

##### 4.3 修饰符

###### 4.3.1 open 修饰符

Kotlin 默认会为每个变量和方法添加`final`修饰符。这么做的目的是为了程序运行的性能，其实在 Java 程序中，你也可以尽可能为每个类添加`final`。因此在 Kotlin 中默认每个类都是不可被继承的，如果你确定这个类是会被继承的，那么你需要给这个类添加`open`修饰符。

###### 4.3.2 internal 修饰符

在 Kotlin 中，默认的访问权限是 public，也就是说不加访问权限修饰符的就是 public。
Kotlin 和 Java 的三种访问修饰符 public/private/protected 相比，多增加了一种模块级别的访问权限`internal`
在 IDEA 中可以很明确看到的`module`就是一个模块，当跨`module`的时候就无法访问另一个`module`的`internal`变量或方法

##### 4.4 一些特殊的类

###### 4.4.1 枚举类

在 Kotlin 中，每一个枚举常量都是一个对象，枚举常量用逗号分隔，枚举的本质是一个实现了`Comparable`的 class，其排序就是按照字段在枚举类中定义的顺序来的。
```
enum class Programer {
  JAVA, KOTLIN, C, CPP, ANDROID;
}
```

###### 4.4.2 sealed 密封类

- sealed 修饰的类称为密封类，用来表示受限的类层次结构。例如当一个值为有限集中的类型，而不能有任何其他类型时。
- 在某种意义上，他们是枚举类的扩展：枚举类型的值集合也是受限的，但每个枚举常量值存在一个实例，而密封类的一个子类可以有包含状态的多个实例

###### 4.4.3 data 数据类

- data 修饰的类称之为数据类。它通常用在我们写的一些 POJO 类上。
- 当 data 修饰后，会自动将所有成员用`operator`声明，即为这些成员生成类似 Java 的 getter/setter 方法

###### 4.5.1 强转与智能转换

在 Kotlin 中，用`is`来判断一个对象是否是某个类的实例，用`as`来做强转。
Kotlin 有一个很好的特性，叫`智能转换(smart cast)`，当确定一个对象的类型后，可以自动识别这个类的对象，而不用再手动强转。
```
fun sayHello(name: String) {
  if(name is String) {
    println("$name 是字符串")
  }
}
```

###### 4.5.2 强转的例外

如果智能转换的对象是一个全局变量，这个变量可能在别的地方被改变赋值，所以你必须手动判断与转换它的类型。
```
open class Animal{}

class Dog : Animal() {
  fun bark() {
    println("dog")
  }
}

var animal: Animal? = null
fun main(args: Array<String>){
  if(animal is Dog) {
    //这里必须手动强转换为 Dog 的对象
    (animal as Dog).bark()
  }
}
```

##### 4.6 单例类

伴生对象更多的用途是用来创建一个单例类。如果只是简单的写，直接用伴生对象返回一个`val`修饰的外部类对象就可以了，但更多的时候，我们希望类被调用的时候才去初始化他的对象。
一下代码将线程问题交给虚拟机在静态内部类加载时处理，是一种推荐的写法：
```
class Single private constructor() {
  companion object{
    fun get(): Single{
      return Holder.instance
    }
  }

  private object Holder{
    val instance = Single()
  }
}
```
