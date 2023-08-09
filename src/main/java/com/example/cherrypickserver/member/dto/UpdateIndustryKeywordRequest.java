package com.example.cherrypickserver.member.dto;

import com.example.cherrypickserver.article.domain.Industry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateIndustryKeywordRequest {

    private Industry firstKeyword;
    private Industry secondKeyword;
    private Industry thirdKeyword;
}
