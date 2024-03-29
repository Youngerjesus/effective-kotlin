# item 47: 인라인 클래스의 사용을 고려하라

## inline class 란 무엇인가 

- 어떠한 값들을 래핑해서 사용하는 경우가 있는데 이렇게 래핑하면 오버헤드가 있다. 또 다른 객체를 생성해야하니까. 
  
  - 이 경우에 인라인 클래스를 만들면 이런 오버헤드를 없앨 수 있다.

- 인라잌 클래스를 사용할려면 하나의 프로퍼티만을 생성자에 줘야한다. (하나의 primary 생성자만 있어야 한다.)

- class 키워드 앞에 `value` 와 `@JvmInline` 을 붙이면 인라인 클래스를 만들 수 있다. 

- 여기서의 메소드는 정적 메소드 형태로 생성된다. 

## inline 클래스는 언제 주로 사용할까 

- 측정 단위를 표현할 때 

  - 말 그대로 단위가 여러가지가 될 수 있을 때를 말한다. 
  - 시간 (time) 만 해도 단위가 여러개다. (초, 밀리초, 마이크로초, 나노초 등)
  - 이런 측정 단위가 여러개인 경우에 구별하기 위해서는 어떻게 해야할까?
    - 파라미터의 이름으로 구별할 수 있지만 실제로 사용할 떄 확인을 못할수도 있다. (놓칠 여지가 있음. 그리고 불필요하게 함수 이름이 길어짐.)
    - 리턴의 경우에는 이렇게 이름으로 구별하기도 어려움. 
  - 그래서 그냥 해당 측정에 따른 인라인 클래스를 만들어주는게 가장 좋다. 

- 타입 오용으로 발생하는 문제를 막을 때  

  - 여기서의 예제는 Int 타입의 변수인 경우 실수로 잘못된 값을 넣을 수 있으므로 inline 클래스를 사용하라고 하는데 잘못된 값을 넣을 수 있는 경우가 있나? 
  - 컴파일러가 잘 막아주는 거 같은데.
  - **(아... 밑의 예제를 보면 같은 Int 끼리라도 구별되야 하는 경우가 있는데 이 경우를 막아주지 못한다. 즉 같은 타입이라도 사용 구별을 하고 싶을 때 쓰면 좋다.)**

- **(응집도를 높이고 싶을 때, money 를 Int 로 표현하기 보다는 Money 클래스로 표현하는게 더 나으니까.)**

## inline 클래스의 주의할 점 

- inline 클래스를 인터페이스로 만들고 상속받아서 사용하는 것도 가능한데, 이 경우에는 객체를 래핑해서 사용하게 되니까 인라인 클래스의 이점이 없다.

- **(요즘 코틀린은 이 경우를 컴파일이 안되도록 잡아주네.)**

- 같은 타입을 사용한다면 `typealias` 는 안전하지 않을 수 있다. 

  - 예로 `typealias Seconds = Int`, `typealias Millis = Int` 로 해서 타입을 만들었다고 가정해보자.
  - `Millis` 로 선언한 객체의 값에 `Seconds` 의 값을 넣는게 가능하다. 결국 같은 Int 타입이니까. 문제가 생기지 않는다. 
  - 이 경우에 인라인 클래스를 쓰면 확실하게 타입을 구별시켜줄 수 있다. 
