package com.example.effectivekotlin.item45

sealed class LinkedList<T>

class Node<T>(
    val head: T,
    val tail: LinkedList<T>
) : LinkedList<T>()

class Empty<T> : LinkedList<T>()

sealed class LinkedList2<out T>

class Node2<out T>(
    val head: T,
    val tail: LinkedList2<T>
) : LinkedList2<T>()

object Empty2 : LinkedList2<Nothing>()

fun main() {
    val linkedList = Node("A", Node("B", Node("C", Empty())))
    val linkedList2 = Node2("A", Empty2)
}
