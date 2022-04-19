package chapter02.item02;

public class TelescopingConstructorPattern {

    private final int servingSize;
    private final int servings;
    private final int calories;

    public TelescopingConstructorPattern(int servingSize) {
        this(servingSize, 0, 0);
    }

    public TelescopingConstructorPattern(int servingSize, int servings) {
        this(servingSize, servings, 0);
    }

    public TelescopingConstructorPattern(int servingSize, int servings, int calories) {
        this.servingSize = servingSize;
        this.servings = servings;
        this.calories = calories;
    }
}
