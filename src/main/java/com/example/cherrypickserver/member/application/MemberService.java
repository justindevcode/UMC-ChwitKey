package com.example.cherrypickserver.member.application;

import com.example.cherrypickserver.member.domain.IndustryKeyword;
import com.example.cherrypickserver.member.domain.Keyword;
import com.example.cherrypickserver.member.domain.Member;
import com.example.cherrypickserver.member.domain.MemberRepository;
import com.example.cherrypickserver.member.dto.request.UpdateIndustryKeywordReq;
import com.example.cherrypickserver.member.dto.request.KeywordReq;
import com.example.cherrypickserver.member.dto.response.LoginTokenRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {

    private final KakaoService kakaoService;
    private final MemberRepository memberRepository;

    @Transactional
    public LoginTokenRes kakaoLogin(String authorize_code)
    {
        String kakaoToken = kakaoService.getAccessToken(authorize_code);
        HashMap<String, String> userInfo = kakaoService.getUserInfo(kakaoToken);
        System.out.println("email : " + userInfo.get("email"));
        System.out.println("nickname : " + userInfo.get("nickname"));
        System.out.println("id : " + userInfo.get("id"));
        return null;
    }

    @Transactional
    public void updateIndustryKeywords(UpdateIndustryKeywordReq request, Long id) {
        IndustryKeyword keyword = new IndustryKeyword(
                request.getFirstKeyword(),
                request.getSecondKeyword(),
                request.getThirdKeyword());

        Member member = memberRepository.getById(id);
        member.changeIndustryKeyword(keyword);
    }

    @Transactional
    public void updateKeyword(KeywordReq request, Long id) {
        Member member = memberRepository.getById(id);
        Keyword keyword = new Keyword(member, request.getKeyword());
        member.addKeyword(keyword);
    }

    @Transactional
    public void deleteKeyword(KeywordReq request, Long id) {
        Member member = memberRepository.getById(id);
        member.removeKeyword(request.getKeyword());
    }

}
