# 인스턴스화를 막으려거든 private 생성자를 사용하라

정적 메서드와 정적 필드만을 담은 클래스를 만들고 싶을 수 있다. 예컨데
java.lang.Math와 java.util.Arrays가 대표적이다. 또는 Collection처럼 특정 인터페이스를 
구현하는 객체를 생성해주는 정적 메서드를 모아 놓을 수 있다. 

이러한 유틸리티 클래스들은 따로 인스턴스 변수나 메소드가 없이 모두 공통으로 동작하고 사용
할 수 있기 때문에 인스턴스화가 될 필요가 없다. 하지만 생성자를 명시하지 않으면 기본 생성자가
만들어지기에 인스턴스화가 될 확률이 존재한다.

이를 막기 위해 추상화하는 것은 정답이 아니다. 하위 클래스를 만들어 인스턴스화를 하면
그만이기 때문이다.

```java
public abstract class StringUtils {
    public static void println(String message) {
        System.out.println(message);
    }
}

public class StringUtilsChild extends StringUtils {
    public StringUtilsChild() {
        super();
    }
}
```

```java
public abstract class StringUtils {
    
    // 인스턴스화 방지 생성자.
    private StringUtils() {
        throw new AssertionError();
    }
    
    public static void println(String message) {
        System.out.println(message);
    }
}
```

위와같이 기본 생성자의 접근제어자가 private면 클래스 외부에서는 접근할수도 없다.
기본 생성자가 내부에서라도 호출될 경우 예외를 발생시키기에 실수로라도 내부에서도 인스턴스화가
되는일을 막아준다. 

이런 private 생성자는 사용자 입장에서 존재하는데 사용은 못하는 생성자를 이해하기 힘들 수 있기에
주석을 달아주는 것도 좋은 방법이다.