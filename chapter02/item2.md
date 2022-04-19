# 생성자에 매개변수가 많다면 빌더를 고려하라 

정적 팩토리 메소드와 public 생성자는 한계점이 존재한다. 선택적 매개변수가 많은 경우
대응하기가 어렵다.

만약 영양정보를 저장하는 클래스가 있으면 여러 속성이 존재하지만 1회 내용량, 총 n회 제공량 등 필수항목과
총 지방, 트랜스 지방, 포화지방 등 선택항목으로 이루어진다. 그리고 이 선택항목은 대부분 0이다. 

이런 클래스를 한번 생성자나 정적 팩터리 메소드를 사용하면 점층적 생성자 패턴을 즐겨 사용했는데 
선택 매개 변수를 1개부터 점층적으로 여러개를 받는 형태로 생성자를 늘려가는 방식이다. 

다읔 코드를 보자

```java
package builder;

public class NutritionFacts {
    private final int servingSize; // 1회 제공량 (필수)
    private final int servings; // 총 n회 제공량 (필수)
    private final int calories; // 1회 제공량당 (선택)
    private final int fat; // (선택)
    private final int sodium; // (선택)
    private final int carbohydrate; // (선택)

    public NutritionFacts(int servingSize, int servings) {
        this(servingSize, servings, 0);
    }

    public NutritionFacts(int servingSize, int servings, int calories) {
        this(servingSize, servings, calories, 0);
    }

    public NutritionFacts(int servingSize, int servings, int calories, int fat) {
        this(servingSize, servings, calories, fat, 0);
    }

    public NutritionFacts(int servingSize, int servings, int calories, int fat, int sodium) {
        this(servingSize, servings, calories, fat, sodium, 0);
    }

    public NutritionFacts(int servingSize, int servings, int calories, int fat, int sodium, int carbohydrate) {
        this.servingSize = servingSize;
        this.servings = servings;
        this.calories = calories;
        this.fat = fat;
        this.sodium = sodium;
        this.carbohydrate = carbohydrate;
    }
}
```

이 클래스의 인스턴스를 만들려면 원하는 매개변수를 모두 포함한 생성자 중 가장 짧은 것을 골라 호출하면 된다.

```java
NutritionFacts cocaCola = new Nutrition(240, 8, 100, 0, 35, 27);
```

이러한 생성자는 결국 원하지않는 매개변수도 넘겨줘야 하고 매개변수가 많아지면 더욱 나쁜 코드로 변할 것이다.
이 패 턴은 결국 매개변수가 많아질수록 읽기 어려운 코드가 된다.

그러면 두 번째 대안인 자바 빈즈 패턴은 어떨까? 

setter를 호출해 원하는 매개변수의 값을 설정하는 방식이다.

```java
public class NutritionFacts {
    private int servingSize; // 1회 제공량 (필수)
    private int servings; // 총 n회 제공량 (필수)
    private int calories; // 1회 제공량당 (선택)
    private int fat; // (선택)
    private int sodium; // (선택)
    private int carbohydrate; // (선택)

    public void setServingSize(int servingSize) {
        this.servingSize = servingSize;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public void setCarbohydrate(int carbohydrate) {
        this.carbohydrate = carbohydrate;
    }
}
```

점층적 패턴에서 보이던 단점들이 더 이상 보이지 않는다. 더 읽기 쉬운 코드가 되었다.

```java
NutritionFactsWithBeans cocaCola = new NutritionFactsWithBeans();
cocaCola.setServingSize(240);
cocaCola.setServings(8);
cocaCola.setCalories(100);
cocaCola.setSodium(35);
cocaCola.setCarbohydrate(27);
```
하지만 이 패턴은 심각한 단점이 있다. 객체를 하나 만들기 위해 여러번 메소드를 호출해야하고, 객체가 완전히 생성되기
전까지 일관성이 무너진 상태에 놓이게 된다.

불변으로 클래스를 만들 수 없으며 외부에서 수정하는 것이기 때문에 캡슐화도 깨트리는 나쁜 코드이다.

이 문제들을 해결하기 위해 등장한 것이 바로 빌더 패턴이다. 

## 빌더 패턴

빌더 패턴은 필수 매개변수만으로 생성자(혹은 정적 팩토리)를 호출해 빌더 객체를 얻는다. 그런 다음 빌더 객체가 제공하는 일종의 
세터 메소드로 선택 매개변수들을 설정한다. 마지막으로 build 메서드를 호출해 필요한 객체를 얻는다.

- 빌더 패턴
```java
public class NutritionFactsWithBuilder {
    private int servingSize; // 1회 제공량 (필수)
    private int servings; // 총 n회 제공량 (필수)
    private int calories; // 1회 제공량당 (선택)
    private int fat; // (선택)
    private int sodium; // (선택)
    private int carbohydrate; // (선택)

    public static class Builder {
        // 필드 매개 변수
        private final int servingSize;
        private final int serving;

        // 선택 매개변수 - 기본값으로 초기화한다.
        private int calories;
        private int fat;
        private int sodium;
        private int carbohydrate;

        public Builder(int servingSize, int serving) {
            this.servingSize = servingSize;
            this.serving = serving;
        }

        public Builder calories(int val) {
            calories = val;
            return this;
        }

        public Builder fat(int val) {
            fat = val;
            return this;
        }

        public Builder sodium(int val) {
            sodium = val;
            return this;
        }

        public Builder carbohydrate(int val) {
            carbohydrate = val;
            return this;
        }

        public NutritionFactsWithBuilder builder() {
            return new NutritionFactsWithBuilder(this);
        }
    }

    private NutritionFactsWithBuilder(Builder builder) {
        servingSize = builder.servingSize;
        servings = builder.serving;
        calories = builder.calories;
        fat = builder.fat;
        sodium = builder.sodium;
        carbohydrate = builder.carbohydrate;
    }
}
```

NutritionFacts 클래스는 불변이며 모든 매개변수의 기본값들을 한곳에 모아뒀다. 빌더의 세터 메서드들은
빌더 자신을 반환하기 때문에 연쇄적으로 호출할 수 있다.

이 방식을 플루언트 API 혹은 method chaining이라 한다.

```java
NutritionFactsWithBuilder cocaCola = new Builder(240, 8)
                .calories(100).sodium(35).carbohydrate(27).builder();
```

다음과 같이 사용하면 쓰기 쉽고 읽기 쉬운 코드가 된다. `빌더 패턴은 명명된 선택적 매개변수를 흉내 낸 것이다.`

빌더 패턴은 계층적으로 설계된 클래스와 함께 쓰기 좋다. 각 계층의 클래스에 관련 빌더를 멤버로 정의하자. 추상 클래스는
추상 빌더를 구체 클래스는 구체 빌더를 갖게 한다.

- 계층적으로 설계된 클래스
```java
public abstract class Pizza {
    public enum Topping { HAM, MUSHROOM, ONION, PEPPER, SAUSAGE }
    final Set<Topping> toppings;

    abstract static class Builder<T extends Builder<T>> {
        EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);

        public T addTopping(Topping topping) {
            toppings.add(Objects.requireNonNull(topping));
            return self();
        }

        abstract Pizza build();

        // 하위 클래스는 이 메서드를 재정의 하여 'this'를 반환하도록 해야 한다.
        protected abstract T self();
    }

    Pizza(Builder<?> builder) {
        toppings = builder.toppings.clone(); // 아이템 50 참조조
    }
}
```

구현체 클래스에서는 `self()` 메서드로 자기자신을 반환하도록 한다. 이를 이용해 하위 클래스에서는 형변환
하지 않고 메서드 체이닝을 지원할 수 있다. `self` 타입이 없는 자바에서 이런 우회 방법을 시뮬레이트한 셀프타입 관용구
라고 한다.

- 뉴욕 피자
```java
public class NyPizza extends Pizza {
    public enum Size { SMALL, MEDIUM, LARGE }
    private final Size size;

    public static class Builder extends Pizza.Builder<Builder> {
        private final Size size;

        public Builder(Size size) {
            this.size = Objects.requireNonNull(size);
        }

        @Override
        NyPizza build() {
            return new NyPizza(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    NyPizza(Builder builder) {
        super(builder);
        size = builder.size;
    }
}
```

- 칼초네 피자
```java
public class Calzone extends Pizza {
    private final boolean sauceInside;

    public static class Builder extends Pizza.Builder<Builder> {
        private boolean sauceInside = false; // 기본 값

        public Builder sauceInside() {
            sauceInside = true;
            return this;
        }

        @Override
        Calzone build() {
            return new Calzone(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    private Calzone(Builder builder) {
        super(builder);
        sauceInside = builder.sauceInside;
    }
}
```

각 하위 클래스의 빌더가 정의한 build 메서드는 해당하는 구체 하위 클래스를 반환하도록 선언한다. NyPizza.Builder는
NyPizza를 반환하고, Calzone.Builder는 Calzone를 반환한다는 뜻이다. 
이 기능을 사용하면 형변환에 신경쓰지 않고도 빌더를 사용할 수 있다.

```java
public static void main(String[] args) {
    NyPizza pizza = new NyPizza.Builder(SMALL)
        .addTopping(SAUSAGE).addTopping(ONION).build();
    Calzone calzone = new Calzone.Builder()
        .addTopping(HAM).sauceInside().build();
}
```

이렇게 하위 클래스가 상위 메소드가 정의한 타입을 반환하는 기능을 공변 반환 타이핑이라 한다. 이 기능을 이용해
아이디나, 일련번호와 같은 특정 필드는 빌더가 알아서 채우게 할 수 있다.

하지만, 장점만 있는 것은 아니다. 먼저, 작성해야 하는 코드 량이 늘어나고, 필드의 수가 적을 수록 가치가 떨어진다.
하지만 유지보수과정에서 필드는 늘어나기 때문에 뒤늦게 보다 처음부터 빌드 패턴을 적용하는게 빠를 수 있으니 
고려해봐야한다.

