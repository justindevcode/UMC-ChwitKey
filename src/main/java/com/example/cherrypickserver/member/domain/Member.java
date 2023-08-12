package com.example.cherrypickserver.member.domain;

import com.example.cherrypickserver.global.entity.BaseEntity;
import com.example.cherrypickserver.member.exception.NotValidBirthdateException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String memberNumber;

    private String name;

    private String birthdate;

    private String gender;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Keyword> keywords = new ArrayList<>();

    @Embedded
    private IndustryKeyword industryKeyword;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Builder
    public Member(String email, Provider provider) {
        this.email = email;
        this.provider = provider;
    }

    public static Member toEntity(String email, Provider provider) {
        return Member.builder()
                .email(email)
                .provider(provider)
                .build();
    }

    public void toUpdateMemberInfo(String nickname, String birth, String gender) {
        this.name = nickname;
        this.birthdate = birth;
        this.gender = gender;
    }

    public void updateMemberName(String updateName){
        this.name = updateName;
    }

    public void updateMemberIndustry(IndustryKeyword industryKeyword){
        this.industryKeyword = industryKeyword;
    }

    public Member(String memberNumber, String name, String birthdate, String gender,
        IndustryKeyword industryKeyword, String email) {
        this.memberNumber = memberNumber;
        this.name = name;
        this.birthdate = birthdate;
        this.gender = gender;
        this.industryKeyword = industryKeyword;
        this.email = email;
    }

    public void updateKeyword(Keyword keyword){
        this.keywords.add(keyword);
    }

    public void deleteKeyword(Keyword keyword){
        keywords.remove(keyword);
    }

    //    public void changeMemberDetail(String name, String birthdate) {
//        validateBirthdate(birthdate);
//        this.name = name;
//        this.birthdate = birthdate;
//    }
//
//    private void validateBirthdate(String birthdate) {
//        if (!birthdate.matches("^(19|20)\\d\\d[-](0[1-9]|1[012])[-](0[1-9]|[12][0-9]|3[01])$")) {
//            throw new NotValidBirthdateException();
//        }
//    }
//
//    public void addKeyword(Keyword keyword) {
//        keyword.changeMember(this);
//        keywords.add(keyword);
//    }
//
//    public void removeKeyword(String name) {
//        List<Keyword> result = keywords.stream()
//                .filter(keyword -> keyword.getName().equals(name))
//                .collect(Collectors.toList());
//
//        for (Keyword key : result) {
//            keywords.remove(key);
//        }
//    }
//
//    public void changeIndustryKeyword(IndustryKeyword industryKeyword) {
//        this.industryKeyword = industryKeyword;
//    }
}
