package com.example.effectivekotlin.item51

fun main() {
    val mutableListOf = mutableListOf("abc", "cdf")
    val mutableListOf1 = mutableListOf("abc", "cdf")
    var listOf = listOf("abc", "cdf")

    mutableListOf += mutableListOf1
    listOf = listOf + mutableListOf
    mutableListOf.map {  }
    listOf.map {  }
}
