package com.example.cherrypickserver.member.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

	Optional<Keyword> findByIdAndIsEnable(Long keywordId, Boolean IsEnable);
	Optional<Keyword> findByMember_MemberNumberAndNameAndIsEnable(String memberNumber, String name,Boolean IsEnable);

	List<Keyword> findByMember_MemberNumberAndIsEnable(String memberNumber, Boolean IsEnable);


}
