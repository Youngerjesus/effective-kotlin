package com.example.effectivekotlin.item46

inline fun repeatInline(times: Int, action: (Int) -> Unit) {
    for (index in 0 until times) {
        action(index)
    }
}

fun repeatNoInline(times: Int, action: (Int) -> Unit) {
    for (index in 0 until times) {
        action(index)
    }
}

fun main () {

    var l = 0
    val start = System.currentTimeMillis()
    repeatNoInline(100000) {
        l += it
    }
    val end = System.currentTimeMillis()

    var l2 = 0
    val start2 = System.currentTimeMillis()
    repeatInline(100000) {
        l2 += it
    }
    val end2 = System.currentTimeMillis()

    println("nonInline elapsed: ${end - start}ms")
    println("inline elapsed: ${end2 - start2}ms")
}
