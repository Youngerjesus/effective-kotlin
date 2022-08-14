package com.example.effectivekotlin.item50

data class Student(
    val name: String?
)

fun main() {
    val students = listOf(Student("name1"), Student("name2"), Student(null))

    // 제일 안좋음
    students
        .map { it.name }
        .filter { it != null }
        .map { it }

    // 그 다음으로 안좋음
    students
        .map { it.name }
        .filterNotNull()

    // 제일 좋음
    students
        .mapNotNull { it.name }
}
