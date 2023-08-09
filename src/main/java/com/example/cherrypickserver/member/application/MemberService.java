package com.example.cherrypickserver.member.application;

import com.example.cherrypickserver.global.utils.TokenUtils;
import com.example.cherrypickserver.member.domain.*;
import com.example.cherrypickserver.member.dto.request.PostSignUpReq;
import com.example.cherrypickserver.member.dto.request.UpdateIndustryKeywordReq;
import com.example.cherrypickserver.member.dto.request.KeywordReq;
import com.example.cherrypickserver.member.dto.response.LoginTokenRes;
import com.example.cherrypickserver.member.exception.MemberNotFoundException;
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
    private final TokenUtils tokenUtils;


    @Transactional
    public LoginTokenRes kakaoLogin(String authorize_code)
    {
        String kakaoToken = kakaoService.getAccessToken(authorize_code);
        String email = kakaoService.getUserInfo(kakaoToken);
        return signIn(email);
    }

    @Transactional
    public LoginTokenRes signIn(String email) {
        boolean isMember = true;
        Member member = memberRepository.findByEmailAndProvider(email, Provider.KAKAO);

        if (member == null) {
            isMember = false;
            member = memberRepository.save(Member.toEntity(email, Provider.KAKAO));
        }

        return LoginTokenRes.toDto(tokenUtils.createToken(member), isMember);
    }

    @Transactional
    public LoginTokenRes signUp(Long memberId, PostSignUpReq postSignUpReq) {
        Member member = memberRepository.findByIdAndIsEnable(memberId, true).orElseThrow(MemberNotFoundException::new);
        member.toUpdateMemberInfo(postSignUpReq.getNickname(), postSignUpReq.getBirth(), postSignUpReq.getGender());
        return LoginTokenRes.toDto(tokenUtils.createToken(member), true);
    }

//    @Transactional
//    public void updateIndustryKeywords(UpdateIndustryKeywordReq request, Long id) {
//        IndustryKeyword keyword = new IndustryKeyword(
//                request.getFirstKeyword(),
//                request.getSecondKeyword(),
//                request.getThirdKeyword());
//
//        Member member = memberRepository.getById(id);
//        member.changeIndustryKeyword(keyword);
//    }
//
//    @Transactional
//    public void updateKeyword(KeywordReq request, Long id) {
//        Member member = memberRepository.getById(id);
//        Keyword keyword = new Keyword(member, request.getKeyword());
//        member.addKeyword(keyword);
//    }
//
//    @Transactional
//    public void deleteKeyword(KeywordReq request, Long id) {
//        Member member = memberRepository.getById(id);
//        member.removeKeyword(request.getKeyword());
//    }

}
