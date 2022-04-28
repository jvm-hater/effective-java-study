package chapter02.item07;

import java.util.WeakHashMap;

public class WeakHashMapExample {

    public static void main(String[] args) {
        WeakHashMap<Integer, String> weakHashMap = new WeakHashMap<>();

        Integer key1 = 1000;
        Integer key2 = 2000;

        weakHashMap.put(key1, "test a");
        weakHashMap.put(key2, "test b");

        key1 = null;

        System.gc();

        weakHashMap.entrySet().forEach(System.out::println);
    }
}
