# item 51: 성능이 중요한 부분에는 기본 자료형 배열을 사용하라

코틀린에서 Int 가 Integer 보다 더 메모리를 적게 쓰고 캐싱을 통해 객체를 생성하지 않으니 더 빠르다.

일반적으로 컬렉션을 쓰면 제네릭을 쓰기 때문에 Int 를 사용할 수 없는데 (자동적으로 Integer 가 된다. 제네릭의 경우에는)

이 경우에는 IntArray, LongArray 와 같이 배열을 고려해보는 것도 괜찮을 수 있다. 

- **배열을 고려할 땐 메모리와 성능이 더 중요한 경우에만 고려하자. 왜냐하면 List 와 같은 컬렉션은 더 많은 기능을 제공하니까.**

- 1,000,000 개의 정수를 만든다고 했을 때 IntArray 는 400,000,016 바이트가 생성되고 List<Int> 는 2,000,006,944 바이트가 생성된다. 

- 그리고 요소에 접근을 하는 비용도 배열이 25% 정도 더 빠르다. (여기서는 1,000,000 개의 데이터를 average() 연산을 바탕으로 평가함.)



