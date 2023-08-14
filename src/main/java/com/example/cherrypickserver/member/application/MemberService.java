package com.example.cherrypickserver.member.application;

import com.example.cherrypickserver.global.utils.TokenUtils;
import com.example.cherrypickserver.member.domain.*;
import com.example.cherrypickserver.member.dto.assembler.MemberAssembler;
import com.example.cherrypickserver.member.dto.request.*;
import com.example.cherrypickserver.member.dto.response.LoginTokenRes;
import com.example.cherrypickserver.member.dto.response.MemberInfoRes;
import com.example.cherrypickserver.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final TokenUtils tokenUtils;

    @Transactional
    public LoginTokenRes signIn(PostSignInReq postSignInReq) {
        boolean isMember = true;
        Member member = memberRepository.findByMemberNumberAndProviderAndIsEnable(postSignInReq.getMemberNumber(), Provider.getProviderByName(postSignInReq.getProvider()), true);

        if (member == null) {
            isMember = false;
            member = memberRepository.save(Member.toEntity(postSignInReq.getMemberNumber(), Provider.getProviderByName(postSignInReq.getProvider())));
        }

        return LoginTokenRes.toDto(tokenUtils.createToken(member), isMember);
    }

    @Transactional
    public LoginTokenRes signUp(Long memberId, PostSignUpReq postSignUpReq) {
        Member member = memberRepository.findByIdAndIsEnable(memberId, true).orElseThrow(MemberNotFoundException::new);
        IndustryKeyword industryKeyword = IndustryKeyword.toEntity(postSignUpReq.getIndustryKeywords());
        member.toUpdateMemberInfo(postSignUpReq.getNickname(), postSignUpReq.getBirth(), postSignUpReq.getGender(), industryKeyword);
        return LoginTokenRes.toDto(tokenUtils.createToken(member), true);
    }

    @Transactional
    public String save(SaveMemberReq saveMemberReq){
        IndustryKeyword industryKeyword = new IndustryKeyword(saveMemberReq.getIndustryKeyword1(), saveMemberReq.getIndustryKeyword2(), saveMemberReq.getIndustryKeyword3());
        Member member = new Member(saveMemberReq.getMemberNumber(), saveMemberReq.getName(), saveMemberReq.getBirthdate(), saveMemberReq.getGender(), industryKeyword);
        memberRepository.save(member);
        return "success save member : " + member.getName();
    }

    @Transactional
    public String updateName(Long memberId,UpdateNameReq updateNameReq){
        Member member = memberRepository.findByIdAndIsEnable(memberId,true).orElseThrow(MemberNotFoundException::new);
        member.updateMemberName(updateNameReq.getUpdateName());
        return "success update memberName : " + member.getName();
    }

    @Transactional
    public String updateIndustryKeyword(Long memberId, UpdateIndustryReq updateIndustryReq){
        IndustryKeyword industryKeyword = new IndustryKeyword(updateIndustryReq.getIndustryKeyword1(), updateIndustryReq.getIndustryKeyword2(), updateIndustryReq.getIndustryKeyword3());
        Member member = memberRepository.findByIdAndIsEnable(memberId,true).orElseThrow(MemberNotFoundException::new);
        member.updateMemberIndustry(industryKeyword);
        return "success update MemberIndustry : " + member.getName();
    }

    @Transactional
    public MemberInfoRes memberInfo(Long memberId){
        Member member = memberRepository.findByIdAndIsEnable(memberId, true)
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
