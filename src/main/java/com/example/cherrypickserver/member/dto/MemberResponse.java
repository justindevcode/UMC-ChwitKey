package com.example.cherrypickserver.member.dto;

import com.example.cherrypickserver.member.domain.IndustryKeyword;
import com.example.cherrypickserver.member.domain.Keyword;
import com.example.cherrypickserver.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberResponse {

    private Long id;
    private String name;
    private String birthdate;
    private String gender;
    private List<String> keywords;
    private IndustryKeyword industryKeyword;

    public MemberResponse(Member member) {
        this(member.getId(), member.getName(), member.getBirthdate(), member.getGender(),
                member.getKeywords().stream().map(Keyword::getName).collect(Collectors.toList()),
                member.getIndustryKeyword());
    }
}
