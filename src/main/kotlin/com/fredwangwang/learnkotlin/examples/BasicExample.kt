package com.fredwangwang.learnkotlin.examples

// When the primary constructor has default visibility (public), the constructor keyword can be omitted.
// Class fields can be directly initialized in the primary constructor by using val or var as well.
class BasicExample(private val name: String) {
    // T.let allows you chain multiple actions to the object, each let block will return
    // the result of the last expression in the block. The return type could be different
    // as the type of it.
    private val upperReversedName = name.let { it.toUpperCase() }.let { it.reversed() }

    // T.also allows you perform any side effect on the object and then return the original object
    // It is useful to perform some file operation based on the filename for example.
    private val welcomeMsg = "Welcome to Kotlin!".also { println("This is the side effect of creating welcomeMsg") }

    // when the block is a single function call, it can use '(::funcName)' instead of writing
    // the full function call '{ funcName(it) }'
    private val nameLength = name.let(::length)

    // Primary constructor can only be used to initialize variables, but it cannot contain any code
    // init function is where necessary initialization code goes.
    init {
        println("this is first constructor")
        this.welcome()
    }

    // And it can appear multiple times, each init block will be call in the order of appearance.
    init {
        println("this is the second constructor")
    }

    fun examples() {
        this.controlFlow()
        this.nullable()
        this.createClass()
    }

    // visibility modifier is the same as in Java
    // the function with a single statement can be shorten as below
    private fun length(str: String): Int = str.length

    private fun welcome() {
        println(welcomeMsg)
        println("My name is $name, it has $nameLength characters.\nDo you still recognize my name $upperReversedName?")
    }

    private fun controlFlow() {
        if (nameLength > 10) println("$name is pretty long")

        // if/else is evaluated as expression in Kotlin, meaning the result of the evaluation can be
        // passed into other functions as an argument.
        println(if (name[0].isUpperCase()) {
            "$name starts with upper case"
        } else {
            "$name starts with lower case"
        })

        val whenInput = 1
        when (whenInput) {
            1, 2, 3 -> println("input is in 1,2,3")
            else -> println("who knows")
        }

        // the code snippet below is the same as:
        // for (i in 1..3) println(i)
        for (i in listOf(1, 2, 3)) {
            println(i)
        }

        for ((k, v) in mapOf("a" to 1, "b" to 2, "c" to 3)) {
            println("$k is $v")
        }

        val whileStop = 10
        var whileInput = 0
        while (whileInput < whileStop) {
            println(++whileInput)
        }

    }

    private fun nullable() {
        fun getNullableName(): String? = name
        fun printLenIfNotNull(a: String?) = println(if (a != null) "$a has length of ${a.length}" else "input is null ")

        var a: String? = null
        // using safe accessor together with Elvis Operator to provide a fallback value if the accessor returns null.
        println(a?.length ?: 0)
        printLenIfNotNull(a)

        a = getNullableName()
        // Although the function signature of getNullableName return nullable string, but if you are confident it is
        // never null, you can use '!!' to force it into the type. NPE will be thrown if the object is actually null.
        println(a!!.length)
        printLenIfNotNull(a)

        val oneToFive = listOf(1, 2, 3, 4, null, 5)
        // The following line would fail because + operator cannot operate on nullable types, but explict casting
        // acc and i by using '!!' operator would throw a NPE.
        // println(oneToFive.reduce { acc, i -> acc!! + i!! })

        println("sum is ${oneToFive.filterNotNull().reduce { acc, i -> acc + i }}")
    }

    private fun createClass() {
        // there is no new keyword in kotlin. Class instantiation looks the same as a function call
        val inner = Inner("inner[$name]")
        inner.hello()
        inner.printOuterName()
    }


    // inner keyword marks the class as a Inner class. Inner class is able to access members of the outer class.
    // If inner keyword is removed, printOuterName will failed to find reference to 'name'.
    private inner class Inner(private val innerName: String) {
        fun hello() {
            println("hello from the inner class! innerName is $innerName")
        }

        fun printOuterName() {
            println("name member of the outer class is $name")
        }
    }
}