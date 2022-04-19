package chapter02.item02;

import chapter02.item02.NyPizza.Size;
import chapter02.item02.Pizza.Topping;

public class Main {

    public static void main(String[] args) {
        BuilderPattern bp = new BuilderPattern.Builder(240, 8)
            .calories(10)
            .build();

        Pizza nyPizza = new NyPizza.Builder(Size.LARGE)
            .addTopping(Topping.HAM)
            .addTopping(Topping.ONION)
            .build();

        Pizza calzone = new Calzone.Builder()
            .addTopping(Topping.ONION)
            .sauceInside(false)
            .build();
    }
}
