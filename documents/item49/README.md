# item 49: 하나 이상의 처리 단계를 가진 경우에는 시퀀스를 사용하라.

Iterable 과 Sequence 의 차이를 알고 있는가? 

```kotlin
public interface Iterable<out T> {
    /**
     * Returns an iterator over the elements of this object.
     */
    public operator fun iterator(): Iterator<T>
}

public interface Sequence<out T> {
    /**
     * Returns an [Iterator] that returns the values from the sequence.
     *
     * Throws an exception if the sequence is constrained to be iterated once and `iterator` is invoked the second time.
     */
    public operator fun iterator(): Iterator<T>
}
```

- Sequence 는 Lazy 하게 처리된다. 
  - 이 뜻은 최종 결과를 내는 연산을 만나기 전까지는 아무런 작업을 하지 않는다. (최종 결과를 내는 연산은 `toList(), count()` 와 같은 것들이 있다. ) 
  - 실제로 Sequence 의 중개 연산들을 보면 데코레이터 패턴으로 꾸며진 새로운 시퀀스를 낸다.
- Iterable 은 그에 반해서 연산마다 새로운 리스트를 만든다.

## Sequence 의 장점은 다음과 같다. 

- 처리 순서를 유지한다. 
- 최소한의 연산만한다. 
- 무한 시퀀스 형태로 사용할 수 있다. 
- 각각의 단계에서 컬렉션을 만들어 내지 않는다. 

각각의 장점을 하나씩 살펴보자.

### 처리 순서의 중요성 

- Sequence 는 요소 하나하나에 연산을 적용하는 반면에 Iterable 은 모든 요소에 연산을 하나씩 step 별로 처리해나간다. 
  - (이를 생각해보면 Iterable 로 처리할 땐 filter 는 늘 앞에 있는게 맞는거 같다. 불필요한 연산을 줄이기 위해서.)
- 그리고 컬렉션 처리를 하지 않고 일반 조건문과 반복문으로 처리를 해나간다면 이는 Sequence 처리와 유사하겠다. 
  - 그럼 컴파일러의 초적화 도움도 더 받을 여지가 많다.

### 최소 연산 

컬렉션 처리에서 컬렉션에 어떤 연산을 적용하고 전체가 필요한게 아니라 앞의 요소 몇가지만 필요한 경우도 있다.

이 경우에 Iterable 은 모든 연산을 처리하고 앞의 요소 10개를 뽑는거라면 Sequence 는 10개의 요소를 뽑아내면 이후의 연산은 하지 않는다.

이런 간단한 예시만 봐도 Sequence 가 더 적은 연산을 한다는 걸 알 수 있다.

```kotlin
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
```

### 무한 시퀀스 

무한 시퀀스를 만들고 필요한 부분까지만 값을 추출하도록 하는 것도 가능하다. 

- 무한 시퀀스를 사용할 땐 값을 몇 개만 사용할 지를 지정해야한다. 아니면 종료되지 않기 떄문에. 

종결 연산에서 `any()` 와 `all()` 을 사용할 땐 주의하자.

- any 는 true 를 리턴하지 못하면 계속해서 실행되고 all 은 false 를 리턴하지 못한다면 계속해서 실행된다. 

### 각각의 단계에서 컬렉션을 만들어내지 않는다. 

Iterable 은 매 연산마다 새로운 컬렉션을 만들어내므로 컬렉션의 크기가 커질수록 비효율적이 된다.

- `File.readLines()` 는 컬렉션을 내뱉는다. (파일은 크기가 큰 경우가 많겠지.)

- `File.useLines()` 를 쓴다면 Sequence 형태로 처리할 수 있다.

**이 각각의 단계에서 컬렉션을 만들어내지 않는다는 점 만으로도 성능상의 유의미한 차이가 있다는게 놀랍다.**

- 여기 예제에서 실험을하는데, filter -> map -> average() or toList() 를 하는데 Sequence 가 Iterable 보다 성능이 20 ~ 40%가 더 좋았다. (하나 이상의 연산이 포함된 경우라면.)
- filter 이후 map 을 한다면 처리하는 연산의 수 만큼은 똑같을 것. 다만 차이는 불필요한 컬렉션을 중간중간마다 만들지 않는다는 점이 있다. 이 차이만 해도 꽤 크다는게 놀랍다.

### 시퀀스가 빠르지 않는 경우 

컬렉션의 전체 요소부터 시작해야하는 경우라면 컬렉션이 더 빠르지 않다. 

코틀린에서는 `sorted` 만이 유일하게 이런 경우다. 

Sequence 에서 `sorted` 즉 정렬을 할 땐 내부적으로 List 를 만들고 정렬을 하므로 Iterable 보다 더 느리다. 

그에반해서 Sequence 는 컬렉션을 만들지 않을 수도 있다. (연산마다 다르다.)

### 자바의 stream 과 코틀린의 sequence 

- 둘 다 lazy 하게 처리되니까 성능은 비슷할 수 있다. 
- 코틀린 sequence 는 자바의 stream 을 보고 만든것이니 더 사용성 측면에서 좋다.
- 다만 자바의 stream 은 병렬 처리가 가능하다. 다만 병렬 처리를 할 땐 조심하자. 이 아티클 참고.
  - https://dzone.com/articles/think-twice-using-java-8
- 필자는 여기서 대부분의 경우에는 코틀린 sequence 를 쓰고 병렬 처리에서만 자바 stream 을 쓴다.
