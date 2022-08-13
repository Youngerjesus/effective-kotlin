package com.example.effectivekotlin.item49

fun main() {
    // Sequence
    (1..10).asSequence()
        .filter { print("F$it "); it % 2 == 1 }
        .map { print("M$it "); it * 2 }
        .find { it > 5 }

    println("vs")
    // Iterable
    (1..10)
        .filter { print("F$it "); it % 2 == 1 }
        .map { print("M$it "); it * 2 }
        .find { it > 5 }
}
