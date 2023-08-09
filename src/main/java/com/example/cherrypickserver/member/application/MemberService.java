package com.example.cherrypickserver.member.application;

import com.example.cherrypickserver.member.domain.IndustryKeyword;
import com.example.cherrypickserver.member.domain.Keyword;
import com.example.cherrypickserver.member.domain.Member;
import com.example.cherrypickserver.member.domain.MemberRepository;
import com.example.cherrypickserver.member.dto.UpdateIndustryKeywordRequest;
import com.example.cherrypickserver.member.dto.KeywordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

//    @Transactional
//    public MemberResponse signUp(Long id, SignUpRequest request) {
//        Member member = memberRepository.getById(id);
//
//        member.changeMemberDetail(request.getName(), request.getBirthdate());
//        return new MemberResponse(member);
//    }
//
//    public MemberResponse findById(Long id) {
//        Member member = memberRepository.getById(id);
//        return new MemberResponse(member);
//    }

    @Transactional
    public void updateIndustryKeywords(UpdateIndustryKeywordRequest request, Long id) {
        IndustryKeyword keyword = new IndustryKeyword(
                request.getFirstKeyword(),
                request.getSecondKeyword(),
                request.getThirdKeyword());

        Member member = memberRepository.getById(id);
        member.changeIndustryKeyword(keyword);
    }

    @Transactional
    public void updateKeyword(KeywordRequest request, Long id) {
        Member member = memberRepository.getById(id);
        Keyword keyword = new Keyword(member, request.getKeyword());
        member.addKeyword(keyword);
    }

    @Transactional
    public void deleteKeyword(KeywordRequest request, Long id) {
        Member member = memberRepository.getById(id);
        member.removeKeyword(request.getKeyword());
    }
}
