package chapter02.item01;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("MemberStatus 테스트")
class MemberStatusTest {

    @ParameterizedTest
    @CsvSource(value = {"0:BASIC", "30:BASIC", "50:INTERMEDIATE", "70:INTERMEDIATE", "80:ADVANCED", "100:ADVANCED"}, delimiter = ':')
    @DisplayName("점수에 따라 MemberStatus를 다르게 반환한다.")
    void of(int input, MemberStatus expected) {
        assertThat(MemberStatus.of(input)).isEqualTo(expected);
    }
}