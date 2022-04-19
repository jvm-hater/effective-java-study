# 자원을 직접 명시하지 말고 의존 객체 주입을 사용하라

많은 클래스가 하나 이상의 자원에 의존한다.
다음 예제에서는 맞춤법 검사기(SpellChecker)가 사전(dictionary)에 의존하고 있다.

- 정적 유틸리티를 잘못 사용한 예

```java
public class SpellChecker {
private static final Lexion dictionary = ...;

    // 객체 생성 방지
    private SpellChecker() {}
    
    public static boolean isValid(String word) { ... }
    
    public static List<String> suggestions(String typo) { ... }
}
```

- 싱글턴을 잘못 사용한 예

```java
public class SpellChecker {
private final Lexion dictionary = ...;

    private SpellChecker(...) {}
    
    public static SpellChecker INSTANCE = new SpellChecker(...);
    
    public boolean isValid(String word) { ... }
    
    public List<String> suggestions(String typo) { ... }
}
```
두 방식 모두 사전을 단 하다만 사용한다고 가정한다.

하지만 실제로는 여러 목적에 맞게 사전을 사용하므로 사전 하나로 모든 경우에 대응하는 것은 부적절하다.
사용하는 자원에 따라 동작이 달라지는 클래스에는 정적 유틸리티 클래스나 싱글턴 방식이 적합하지 않다.

## 의존 객체 주입

```java
public class SpellChecker {
private final Lexion dictionary;

    public SpellChecker(Lexion dictionary) {
        this.dictionary = Objects.requireNonNull(dictionary);
    }
    
    public boolean isValid(String word) { ... }
    
    public List<String> suggestions(String typo) { ... }
}
```
클래스(SpellChecker)가 여러 자원 인스턴스를 지원해야 하며, 클라이언트가 원하는 자원(dictionary)을 사용해야 하는 경우, 인스턴스를 생성할 때 생성자에 필요한 자원을 넘겨주는 방식을 사용하면 된다.

의존 객체 주입의 한 형태이다.
의존 객체 주입은 생성자, 정적 팩터리, 빌더 모두에 똑같이 응용할 수 있다.

의존 객체 주입이 유연성과 테스트 용이성을 개선해주긴 하지만, 의존성이 수천 개나 되는 큰 프로젝트에서는 코드를 어지럽게 만든다.

스프링 같은 의존 객체 주입 프레임워크를 사용하자.