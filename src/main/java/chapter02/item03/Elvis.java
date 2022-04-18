package chapter02.item03;

public class Elvis {

    private static final Elvis INSTANCE = new Elvis();

    private Elvis() {
    }

    public static Elvis getInstance() {
        return INSTANCE;
    }

    public void speak() {
        System.out.println("elvis");
    }
}