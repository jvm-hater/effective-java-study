# private 생성자나 열거 타입으로 싱글턴임을 보증하라

클래스를 싱글턴으로 만들면 이를 사용하는 클라이언트 테스트하기가 어려워 질 수 있다. 
타입을 인터페이스로 정의한 다음 그 인터페이스를 구현해서 만든 싱글턴이 아니라면 싱글턴
인스턴스를 가짜 구현으로 대체할 수 없기 때문이다. 

싱글턴으로 만드는 방식을 살펴보자

- public static final 필드 방식의 싱글턴

```java
public class Elvis {
    public static final Elvis INSTANCE = new Elvis();
    
    private Elvis() {}
    
    public void leaveTheBuilding() { 
        // ... //
    }
}
```

private 생성자는 public static final 필드인 Elvis.INSTANCE를 초기화 할 때
딱 한 번만 호출된다. 그렇기 때문에 전체 시스템에서 하나뿐임이 보장된다. 

리플렉션 API를 이용해 AccessibleObject.setAccessible을 사용하여 생성자를 호출하는
방법 외에는 접근이 불가능하다. 

두 번째 방법은 정적 팩토리 메서드를 public static 멤버로 제공한다.

- 정적 팩토리 방식

```java
public class Elvis {
    private static final Elvis INSTANCE = new Elvis();
    
    private Elvis() {}
    
    public static Elvis getInstance() {
        return INSTANCE;
    }
    
    public void leaveTheBuilding() {
        // ... //
    }
}
```

Elivs.getInstance는 항상 같은 객체의 참조를 반환하여 제 2의 Elvis 인스턴스란 결코
만들어지지 않는다.(리플렉션 동시성에선 따로 처리가 필요하다.)

첫 번째 방식은 클래스가 싱글턴임이 API에 명백히 들어난다는 장점이 있고 간결하다.

한편, 정적 팩토리는 API를 바꾸지 않도도 싱글턴이 아니게 변경할 수 있다는 점이다.
그리고 제네릭 싱글턴 팩토리로 만들 수 있다. 마지막으로 메서드 참조를 공급자로 사용할 수
있다는 점이다. 가령 Elvis::getInstance를 Supplier<Elvis>로 사용하는 식이다. 이러한 장점들이
필요하지 않다면 public 필드 방식이 좋다.

이 때 모든 인스턴스 필드를 일시적이라고 선언하고 readResolve 메서드를 제공해야 한다.
이렇게 하지 않으면 직렬화 시 직렬화된 인스턴스를 역직렬화 할 때마다 새로운 인스턴스가
만들어진다. 
가짜 Elvis 탄생을 예방하기 위해서 Elvis 클래스에 다음을 추가하자.

```java
private Object readResolve() {
    // '진짜' Elvis를 반환하고, 가짜 Elvis는 가비지 컬렉터에 맡긴다.
    return INSTANCE;
}
```

세 번째 방법은 열거 타입을 선언하는 것이다.

```java
public enum ElvisWithEnum {
    INSTANCE;
    
    public void leaveTheBuilding() {
        // ... //
    }
}
```

public 필드 방식과 비슷하지만, 더 간결하고, 추가 노력 없이 직렬화 할 수 있고, 심지어
아주 복잡한 직렬화 상황, 리플렉션 공격에도 완벽히 막아준다. 
**대부분 상황에서는 원소가 하나 뿐인 열거 타입이 싱글턴을 만드는 가장 좋은 방법이다.**