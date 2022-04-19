# 생성자대신 정적 팩토리 메소드를 고려해보자!

아마 자바를 통해 특정 인스턴스를 생성하다 보면 가장 많이 이용하는 것이 public 생성자
일 것이다. 

- 생성자를 이용한 객체 생성

```java
import java.time.LocalDate;

public class Member {
    private String email;
    private String password;
    private String name;
    private LocalDate birth;
    
    public Member(String email, String password, String name, LocalDate birth) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.birth = birth;
    }
}
```

하지만 이러한 생성자는 보통 하나의 생성 메소드로도 볼 수 있기 때문에 다음과 같은 문제가 생긴다.

```java
import java.time.LocalDate;

public class Member {
    private String email;
    private String password;
    private String name;
    private String nickname;
    private LocalDate birth;
    
    public Member(String email, String password, String name, LocalDate birth) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.birth = birth;
    }

    // 동일한 인자를 받기 때문에 컴파일 오류가 생김
    public Member(String email, String password, String nickname, LocalDate birth) {
        this.email = email;
        this.password = password;
        this.name = nickname;
        this.birth = birth;
    }
}
```

그렇기 때문에 때론 불편할 수 가 있다. 그렇기 때문에 이 때 우리는 정적 팩토리 메소드를 구현해야할 
필요가 있다. 


- 정적 팩토리 메소드 사용
```java
import java.time.LocalDate;

public class Member {
    private String email;
    private String password;
    private String name;
    private String nickname;
    private LocalDate birth;

    public static Member withName(String email, String password, String name, LocalDate birth) {
        Member member = new Member();
        
        member.email = email;
        member.password = password;
        member.name = name;
        member.birth = birth;
        
        return member;
    }

    // 이름이 다르기 때문에 전혀 문제가 생기지 않는 코드
    public static Member withNickname(String email, String password, String nickname, LocalDate birth) {
        Member member = new Member();

        member.email = email;
        member.password = password;
        member.nickname = nickname;
        member.birth = birth;
        
        return member;
    }
}
```
이름을 따로 정의할 수 있기 때문에 아까와 같은 문제를 깔끔하게 해결할 수 있다.

정적 팩토리 메소드는 이외에도 여러 장점이 있다. 살펴보자.

## 새로운 인스턴스를 생성하지 않는다. 

보통 new 키워드를 통해 새로운 인스턴스를 생성해주는게 생성자의 기본이다. 하지만
이 경우엔 미리 인스턴스를 생성해 두어 불필요한 객체 생성을 막을 수 있습니다.

- ex) Boolean 클래스
```java
public final class Boolean implements java.io.Serializable,
        Comparable<Boolean> {
    /**
     * The {@code Boolean} object corresponding to the primitive
     * value {@code true}.
     */
    public static final Boolean TRUE = new Boolean(true);

    /**
     * The {@code Boolean} object corresponding to the primitive
     * value {@code false}.
     */
    public static final Boolean FALSE = new Boolean(false);

    public static Boolean valueOf(boolean b) {
        return (b ? TRUE : FALSE);
    }
}
```

이 경우에도 미리 인스턴스를 캐싱하여 전역 변수에 의해 캐싱된 인스턴스를 반환해 줌으로 
불 필요한 객체 생성을 방지하고 있다. 

## 하위 타입의 객체를 반환할 수 있다.

```java
public static <T> List<T> asList(T... a) {
        return new ArrayList<>(a);
}
```
Arrays 유틸 클래스에서 asList를 통해 List 배열을 만들 때 ArrayList 즉, 하위 타입을
반환해주고 있다. 

이렇게 설계 한다면 구현체에 관계없이 비교적 편하게 개발할 수 있게 된다.

## 입력 매게변수에 따라 다른 클래스의 객체를 반환할 수 있다.

```java
package classroom;

import java.util.Objects;

public class ClassRoomFactory {

    private static ClassRoomFactory instance = new ClassRoomFactory();

    private ClassRoomFactory() {
    }

    public synchronized static ClassRoomFactory getInstance() {
        if (Objects.isNull(instance)) {
            instance = new ClassRoomFactory();
        }

        return instance;
    }

    public static ClassRoom getClassRoom(int limitCount) {
        if (SmallClass.supported(limitCount)) {
            return new SmallClass();
        }
        else if (MiddleClass.supported(limitCount)) {
            return new MiddleClass();
        }
        else if (BigClass.supported(limitCount)) {
            return new BigClass();
        }

        throw new IllegalArgumentException("잘못된 limit 입니다.");
    }
}
```

다음 코드를 보면 여러가지 전략에 의해서 알맞은 인스턴스를 반환하고 있다. 이 처럼
여러 상황에 대해 유연하게 동작하는 전략을 취할 수 있다.

## 정적 팩토리 메소드를 작성하는 시점에는 반환할 객체의 클래스가 없어도 된다.

먼저 JDBC에 연결하는 비즈니스 로직을 살펴보자

```java
public static void main(String[] args) {
    String driverName = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/test";
    String user = "golf";
    String password = "1234";

    try {
        Class.forName(driverName);

        // 서비스  접근 API인 getConnection이 서비스 구현체인 Connection을 반환
        Connection connection = DriverManager.getConnection(url, user, password);
        
    } catch (ClassNotFoundException e | SQLException e) {
        e.printStackTrace();
    }
}
```

이 코드 같은 경우 Class.forName으로 드라이버 이름만 호출 하지만 리플렉션 DriverManger가
mysql 드라이버임을 알고 커넥션을 만들어서 리턴한다.

`Connection` : 서비스 인터페이스
`DriverManager.getConnection` : 서비스 접근 API

각각 역할을 맡고 있고 또한 

`DriverManager.registerDriver` : 제공자 등록 API
`Driver` : 서비스 제공자 인터페이스 역할

역할을 맡고 있다. 이는 대표적인 하나의 예시로 정적 팩토리 메소드는 구현되어있지 않은 
객체의 클래스를 반환할 수 있다.

이 부분이 서비스 제공자 프레임워크의 근간이 되는 개념으로 제공자가 서비스의 구현체이다.
그리고 이 구현체들을 클라이언트에 제공하는 역할을 프레임워크가 통제해서 클라이언트를 
구현체로부터 분리해준다.(DIP)

서비스 제공자 프레임워크는 다음 3개의 핵심 컴포넌트로 이뤄진다.

- 서비스 인터페이스 : 구현체의 동작을 정의한다.
- 제공자 등록 API : 제공자가 구현체를 등록할 때 사용하는 제공자 등록 API
- 서비스 접근 API : 클라이언트가 인스턴스를 얻을 때 사용하는 접근 API

정적 팩토리 메소드에 대한 장점을 알아보았다. 다음은 단점에 대해 알아보자

## 정적 팩토리 메소드 사용시 생성자가 private이라 상속이 어렵다.

컬렉션 프레임워크의 유틸리티 구현 클래스들을 상속할 수 없다. 그렇기 때문에
상속에 이점을 포기해야한다. 

## 정적 팩토리 메소드는 프로그래머가 찾기 힘들다.

public 생성자는 API 설명에도 나와있기 때문에 쓰임새가 명확하다. 하지만 정적 팩토리
메소드는 방법을 직접 찾아야 한다. 그렇기 때문에 관례대로 네이밍을 잘하는 것이 중요하다.

- from : 매개변수 하나를 받아 해당 타입의 인스턴스를 반환
- of : 여러 매개변수를 받아 해당 타입의 인스턴스를 반환
- valueOf : from과 of의 더 자세한 버전
- getInstance : 매개변수로 명시한 인스턴스를 반환하지만, 같은 인스턴스임을 보장하지 않는다.
- create or newInstance : instance 혹은 getInstance와 같지만 새로운 인스턴르를 반환함을 보장한다.
- getType : getInstance와 같으나, 생성할 클래스가 아닌 다른 클래스에 팩토리 메소드를 정의할 때 쓴다.
- newType : newInstnace와 같으나, 생성할 클래스가 아닌 다른 클래스에 팩토리 메서드를 정의할 때 쓴다.
- type : getType과 newType에 간결한 버전