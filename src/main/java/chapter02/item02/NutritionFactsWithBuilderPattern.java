package chapter02.item02;

public class NutritionFactsWithBuilderPattern {

    private final int servingSize;

    private final int servings;

    private final int calories;

    private final int fat;

    private final int sodium;

    private final int carbohydrate;

    private NutritionFactsWithBuilderPattern(Builder builder) {
        servingSize = builder.servingSize;
        servings = builder.servings;
        calories = builder.calories;
        fat = builder.fat;
        sodium = builder.sodium;
        carbohydrate = builder.carbohydrate;
    }

    public static class Builder {

        private final int servingSize;

        private final int servings;

        private int calories;

        private int fat;

        private int sodium;

        private int carbohydrate;

        public Builder(int servingSize, int servings) {
            this.servingSize = servingSize;
            this.servings = servings;
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
            calories = val;
            return this;
        }

        public NutritionFactsWithBuilderPattern build() {
            return new NutritionFactsWithBuilderPattern(this);
        }
    }
}
