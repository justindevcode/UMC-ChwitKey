package com.example.cherrypickserver.member.domain;

import com.example.cherrypickserver.member.exception.NotValidBirthdateException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {

    @ParameterizedTest
    @ValueSource(strings = {"202-01-01", "20230101", "2023-0101"})
    void 유효하지_않은_생년월일은_예외가_발생한다(String birthdate) {
        // given & when & then
        assertThatThrownBy(() -> new Member(birthdate, "M"))
                .isInstanceOf(NotValidBirthdateException.class)
                .hasMessage("유효하지 않은 생년월일 입니다.");
    }
}
