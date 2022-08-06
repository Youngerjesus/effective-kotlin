package com.example.effectivekotlin.item47

typealias Seconds2 = Int
typealias Millis2 = Int

fun main() {
    var seconds: Seconds2 = 10
    var millis: Millis2 = 5

    millis = seconds // ok.
}
