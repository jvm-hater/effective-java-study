package chapter02.item06;

public class AutoboxingExample {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        sumWithAutoboxing();
        long end = System.currentTimeMillis();
        System.out.println((end - start));

        start = System.currentTimeMillis();
        sum();
        end = System.currentTimeMillis();
        System.out.println((end - start));
    }

    public static long sumWithAutoboxing() {
        Long sum = 0L;
        for (long i = 0; i <= Integer.MAX_VALUE; i++) {
            sum += i;
        }
        return sum;
    }

    public static long sum() {
        long sum = 0L;
        for (long i = 0; i <= Integer.MAX_VALUE; i++) {
            sum += i;
        }
        return sum;
    }
}
