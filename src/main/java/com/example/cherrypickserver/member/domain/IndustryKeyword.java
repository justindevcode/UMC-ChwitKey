package com.example.cherrypickserver.member.domain;

import com.example.cherrypickserver.article.domain.Industry;
import com.example.cherrypickserver.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Getter
@Embeddable
public class IndustryKeyword {

    @Enumerated(EnumType.STRING)
    private Industry first;

    @Enumerated(EnumType.STRING)
    private Industry second;

    @Enumerated(EnumType.STRING)
    private Industry third;

    public IndustryKeyword(Industry first, Industry second, Industry third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public IndustryKeyword(String industryKeyword1, String industryKeyword2, String industryKeyword3) {
        this.first = !industryKeyword1.isEmpty() ? Industry.fromValue(industryKeyword1) : null;
        this.second = !industryKeyword2.isEmpty() ? Industry.fromValue(industryKeyword2) : null;
        this.third = !industryKeyword3.isEmpty() ? Industry.fromValue(industryKeyword3) : null;
    }
}
