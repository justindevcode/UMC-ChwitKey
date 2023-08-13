package com.example.cherrypickserver.member.domain;

import com.example.cherrypickserver.article.domain.Industry;
import com.example.cherrypickserver.member.dto.request.IndustryKeywordReq;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    public IndustryKeyword(String industryKeyword1, String industryKeyword2, String industryKeyword3) {
        this.first = !industryKeyword1.isEmpty() ? Industry.fromValue(industryKeyword1) : null;
        this.second = !industryKeyword2.isEmpty() ? Industry.fromValue(industryKeyword2) : null;
        this.third = !industryKeyword3.isEmpty() ? Industry.fromValue(industryKeyword3) : null;
    }

    public static IndustryKeyword toEntity(List<IndustryKeywordReq> industryKeywordReqs) {
        return new IndustryKeyword(
                industryKeywordReqs.get(0).getIndustryKeyword(),
                industryKeywordReqs.get(1).getIndustryKeyword(),
                industryKeywordReqs.get(2).getIndustryKeyword()
        );
    }


}
