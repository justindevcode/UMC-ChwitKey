package com.example.cherrypickserver.member.domain;

import com.example.cherrypickserver.common.exception.NoSuchEntityException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByIdAndIsEnable(Long memberId, Boolean IsEnable);

    Optional<Member> findByMemberNumberAndIsEnable(String memberNumber, Boolean IsEnable);

    Member findByMemberNumberAndProviderAndIsEnable(String memberNumber, Provider provider, Boolean IsEnable);
}
