# item 50: 컬렉션 처리 단계 수를 제한하라

## 왜 제한하라고 하는 것일까? 

Iterable 은 연산마다 내부적으로 컬렉션을 추가로 만들어서 작동하고, Sequence 도 데코레이터 패턴을 이용해서 새로운 객체를 만들고 기존의 객체를 wrap 해서 처리하므로 비용이든다. 

그러므로 어떻게든 연산 수를 줄이는게 낫다. 

다음 간단한 에시를 보자.

```kotlin
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
```

- 줄일 수 있는 연산이 있으면 줄이는게 낫다.

## 줄일 수 있는 연산은 어떤게 있을까? 

줄이기 전| 줄인 후|
|:---:|:---: |
|`.filter {it != null}.map { it!! }` | `.filterNotNull()` |
|`.map { transformation(it) }.filterNotNull()` | `.mapNotNull { transformation(it }` |
| `.map { transformation(it) }.joinToString()` | `.joinToString { transformation(it) }` |
| `.filter { predicate1(it) }.filter {predicate2(it) }` | `.filter { predicate1(it} && predicate2(it) }`|
| `.filter { it is TYPE }.map { it as TYPE}` | `.filterIsInstance<TYPE>`|
| `.sortedBy { KEY1 }.sortedBy {KEY2 }` | `sortedWith(compareBy({KEY1}, {KEY2}))`|
| `listOf().filterNotNull()`| `listOfNotNull()`|
| `withIndex().filter { index, elem -> } ` | `filterIndexed { index, elem -> }` |


