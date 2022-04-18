package chapter02.item04;

public abstract class Person {

    private Person() {
        throw new AssertionError();
    }

    public static void speak(String message) {
        System.out.println(message);
    }
}
