package com.example.cherrypickserver.member.dto.assembler;

import com.example.cherrypickserver.member.domain.IndustryKeyword;
import com.example.cherrypickserver.member.dto.request.IndustryKeywordReq;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemberAssembler {
  public IndustryKeyword toUpdateIndustryKeyword(List<IndustryKeywordReq> industryKeywords) {
    return new IndustryKeyword(
        industryKeywords.get(0).getIndustryKeyword(),
        industryKeywords.get(1).getIndustryKeyword(),
        industryKeywords.get(2).getIndustryKeyword());
  }
}
