package chapter02.item02;

import java.util.EnumSet;
import java.util.Set;

public abstract class Pizza {

    public enum Topping {
        HAM, MUSHROOM, ONION, PEPPER, SAUSAGE
    }

    final Set<Topping> toppings;

    Pizza(Builder<?> builder) {
        toppings = builder.toppings.clone();
    }

    abstract static class Builder<T extends Builder<T>> {

        private EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);

        public T addTopping(Topping topping) {
            toppings.add(topping);
            return self();
        }

        abstract Pizza build();

        protected abstract T self();
    }
}
