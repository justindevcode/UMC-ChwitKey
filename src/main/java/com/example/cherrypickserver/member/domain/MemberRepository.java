package com.example.cherrypickserver.member.domain;

import com.example.cherrypickserver.common.exception.NoSuchEntityException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByIdAndIsEnable(Long memberId, Boolean IsEnable);

    Member findByEmailAndProvider(String email, Provider provider);

//    Optional<Member> findById(Long id);
//
//    default Member getById(Long id) {
//        return findById(id)
//                .orElseThrow(() -> new NoSuchEntityException("해당 아이디의 회원이 존재하지 않습니다."));
//    }
//
//    Optional<Member> findByEmail(String email);
//
//    default Member getByEmail(String email) {
//        return findByEmail(email)
//                .orElseThrow(() -> new NoSuchEntityException("해당 이메일의 회원이 존재하지 않습니다."));
//    }
}
