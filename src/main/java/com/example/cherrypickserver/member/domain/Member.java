package com.example.cherrypickserver.member.domain;

import com.example.cherrypickserver.global.entity.BaseEntity;
import com.example.cherrypickserver.member.exception.NotValidBirthdateException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String birthdate;

    @Column(nullable = false)
    private String sex;

    public Member(String name, String birthdate, String sex) {
        validateBirthdate(birthdate);
        this.name = name;
        this.birthdate = birthdate;
        this.sex = sex;
    }

    private void validateBirthdate(String birthdate) {
        if (!birthdate.matches("^(19|20)\\d\\d[-](0[1-9]|1[012])[-](0[1-9]|[12][0-9]|3[01])$")) {
            throw new NotValidBirthdateException();
        }
    }
}
