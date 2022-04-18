package chapter02.item05;

import java.util.Collections;
import java.util.List;

public class SpellCheckerSingleton {

    private final Lexicon dictionary = new Lexicon();

    private static final SpellCheckerSingleton INSTANCE = new SpellCheckerSingleton();

    private SpellCheckerSingleton() {
    }

    public static SpellCheckerSingleton getInstance() {
        return INSTANCE;
    }

    public static boolean isValid(String word) {
        return true;
    }

    public static List<String> suggestions(String typo) {
        return Collections.emptyList();
    }
}
