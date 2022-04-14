package chapter02.item02;

import static org.assertj.core.api.Assertions.assertThat;

import chapter02.item02.NyPizza.Size;
import chapter02.item02.Pizza.Topping;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PizzaTest {

    @Test
    @DisplayName("빌더 패턴을 이용하여 뉴욕 피자, 칼초네 피자를 만든다.")
    void create() {
        NyPizza nyPizza = new NyPizza.Builder(Size.SMALL)
            .addTopping(Topping.SAUSAGE)
            .addTopping(Topping.ONION)
            .build();

        CalzonePizza calzonePizza = new CalzonePizza.Builder()
            .addTopping(Topping.HAM)
            .sauceInside()
            .build();

        assertThat(nyPizza).isNotNull();
        assertThat(calzonePizza).isNotNull();
    }
}