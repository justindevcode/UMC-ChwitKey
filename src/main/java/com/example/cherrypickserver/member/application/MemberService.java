package com.example.cherrypickserver.member.application;

import com.example.cherrypickserver.auth.dto.SignUpRequest;
import com.example.cherrypickserver.member.dto.MemberResponse;
import com.example.cherrypickserver.member.domain.Member;
import com.example.cherrypickserver.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponse signUp(Long id, SignUpRequest request) {
        Member member = memberRepository.getById(id);

        member.changeMemberDetail(request.getName(), request.getBirthdate());
        return new MemberResponse(member);
    }

    public MemberResponse findById(Long id) {
        Member member = memberRepository.getById(id);
        return new MemberResponse(member);
    }
}
