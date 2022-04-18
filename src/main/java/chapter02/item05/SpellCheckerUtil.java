package chapter02.item05;

import java.util.Collections;
import java.util.List;

public class SpellCheckerUtil {

    private static final Lexicon dictionary = new Lexicon();

    private SpellCheckerUtil() {
    }

    public static boolean isValid(String word) {
        return true;
    }

    public static List<String> suggestions(String typo) {
        return Collections.emptyList();
    }
}
