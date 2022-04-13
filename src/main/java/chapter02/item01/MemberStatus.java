package chapter02.item01;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public enum MemberStatus {

    ADVANCED(80, 100),
    INTERMEDIATE(50, 79),
    BASIC(0, 49);

    private final int minScore;
    private final int maxScore;

    MemberStatus(int minScore, int maxScore) {
        this.minScore = minScore;
        this.maxScore = maxScore;
    }

    public static MemberStatus of(int score) {
        return Arrays.stream(values())
                .filter(decideMemberStatus(score))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("해당하는 MemberStatus 객체가 없습니다."));
    }

    private static Predicate<MemberStatus> decideMemberStatus(int score) {
        return element -> element.minScore <= score && element.maxScore >= score;
    }
}
