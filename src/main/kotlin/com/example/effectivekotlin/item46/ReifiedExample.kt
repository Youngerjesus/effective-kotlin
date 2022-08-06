package com.example.effectivekotlin.item46

inline fun <reified T> printClassName() {
    println(T::class.simpleName)
}

fun main() {
    printClassName<String>()
    printClassName<Int>()

    val listOf = listOf<String>("abc")
    listOf.filterIsInstance<String>()
}
