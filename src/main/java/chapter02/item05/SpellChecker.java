package chapter02.item05;

import java.util.Collections;
import java.util.List;

public class SpellChecker {

    private final Lexicon dictionary;

    public SpellChecker(Lexicon dictionary) {
        this.dictionary = dictionary;
    }

    public static boolean isValid(String word) {
        return true;
    }

    public static List<String> suggestions(String typo) {
        return Collections.emptyList();
    }
}
