package com.example.effectivekotlin.item49

fun main() {
    generateSequence(1) { it + 1 }
        .map { it * 2 }
        .take(10)
        .forEach { print("$it ") }
}
