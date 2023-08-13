package com.example.cherrypickserver.member.application;

import com.example.cherrypickserver.global.utils.TokenUtils;
import com.example.cherrypickserver.member.domain.*;
import com.example.cherrypickserver.member.dto.request.PostSignUpReq;
import com.example.cherrypickserver.member.dto.request.SaveMemberReq;
import com.example.cherrypickserver.member.dto.request.UpdateIndustryKeywordReq;
import com.example.cherrypickserver.member.dto.request.KeywordReq;
import com.example.cherrypickserver.member.dto.request.UpdateIndustryReq;
import com.example.cherrypickserver.member.dto.request.UpdateNameReq;
import com.example.cherrypickserver.member.dto.response.LoginTokenRes;
import com.example.cherrypickserver.member.dto.response.MemberInfoRes;
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

    @Transactional
    public String save(SaveMemberReq saveMemberReq){
        IndustryKeyword industryKeyword = new IndustryKeyword(saveMemberReq.getIndustryKeyword1(), saveMemberReq.getIndustryKeyword2(), saveMemberReq.getIndustryKeyword3());
        Member member = new Member(saveMemberReq.getMemberNumber(), saveMemberReq.getName(), saveMemberReq.getBirthdate(), saveMemberReq.getGender(), industryKeyword, "temp@test.com");
        memberRepository.save(member);
        return "success save member : " + member.getName();
    }

    @Transactional
    public String updateName(UpdateNameReq updateNameReq){
        Member member = memberRepository.findByMemberNumberAndIsEnable(updateNameReq.getMemberNumber(),true).orElseThrow(MemberNotFoundException::new);
        member.updateMemberName(updateNameReq.getUpdateName());
        return "success update memberName : " + member.getName();
    }

    @Transactional
    public String updateIndustryKeyword(UpdateIndustryReq updateIndustryReq){
        IndustryKeyword industryKeyword = new IndustryKeyword(updateIndustryReq.getIndustryKeyword1(), updateIndustryReq.getIndustryKeyword2(), updateIndustryReq.getIndustryKeyword3());
        Member member = memberRepository.findByMemberNumberAndIsEnable(updateIndustryReq.getMemberNumber(),true).orElseThrow(MemberNotFoundException::new);
        member.updateMemberIndustry(industryKeyword);
        return "success update MemberIndustry : " + member.getName();
    }

    @Transactional
    public MemberInfoRes memberInfo(String memberNumber){
        Member member = memberRepository.findByMemberNumberAndIsEnable(memberNumber, true)
            .orElseThrow(MemberNotFoundException::new);
        return MemberInfoRes.toDto(member);
    }

    @Transactional
    public String deleteMember(Long memberId) {
        Member member = memberRepository.findByIdAndIsEnable(memberId, true)
                .orElseThrow(MemberNotFoundException::new);
        member.setIsEnable(false);

        return "success delete member";
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
