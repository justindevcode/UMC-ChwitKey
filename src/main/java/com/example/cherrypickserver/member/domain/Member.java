package com.example.cherrypickserver.member.domain;

import com.example.cherrypickserver.global.entity.BaseEntity;
import com.example.cherrypickserver.member.exception.NotValidBirthdateException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String birthdate;

    @Column(nullable = false)
    private String gender;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Keyword> keywords = new ArrayList<>();

    @Embedded
    private IndustryKeyword industryKeyword;

    @Column(nullable = false)
    private String email;

    public Member(String gender, String email) {
        this.gender = gender;
        this.email = email;
    }

    public void changeMemberDetail(String name, String birthdate) {
        validateBirthdate(birthdate);
        this.name = name;
        this.birthdate = birthdate;
    }

    private void validateBirthdate(String birthdate) {
        if (!birthdate.matches("^(19|20)\\d\\d[-](0[1-9]|1[012])[-](0[1-9]|[12][0-9]|3[01])$")) {
            throw new NotValidBirthdateException();
        }
    }

    public void addKeyword(Keyword keyword) {
        keyword.changeMember(this);
        keywords.add(keyword);
    }

    public void removeKeyword(Keyword keyword) {
        keywords.remove(keyword);
    }

    public void changeIndustryKeyword(IndustryKeyword industryKeyword) {
        this.industryKeyword = industryKeyword;
    }
}
