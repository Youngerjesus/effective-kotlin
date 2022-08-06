package com.example.effectivekotlin.item47

@JvmInline
value class Node(val name: String) {

    fun greeting(): String {
        return "hi $name"
    }
}

fun main() {
    val node = Node("martin")
    println(node.greeting())
}
