package com.example.cherrypickserver.member.application;


import com.example.cherrypickserver.member.domain.IndustryKeyword;
import com.example.cherrypickserver.member.domain.Keyword;
import com.example.cherrypickserver.member.domain.KeywordRepository;
import com.example.cherrypickserver.member.domain.Member;
import com.example.cherrypickserver.member.domain.MemberRepository;
import com.example.cherrypickserver.member.dto.request.PostKeywordReq;
import com.example.cherrypickserver.member.dto.request.SaveMemberReq;
import com.example.cherrypickserver.member.dto.response.MemberKeywordRes;
import com.example.cherrypickserver.member.exception.MemberNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class KeywordService {

	private final MemberRepository memberRepository;
	private final KeywordRepository keywordRepository;

	@Transactional
	public String save(Long memberId, PostKeywordReq postKeywordReq){
		Member member = memberRepository.findByIdAndIsEnable(memberId,true).orElseThrow(
			MemberNotFoundException::new);
		Keyword keyword = new Keyword(member, postKeywordReq.getKeyword());
		keywordRepository.save(keyword);
		member.updateKeyword(keyword);
		return "success update keyword";
	}

	@Transactional
	public String delete(Long memberId, PostKeywordReq postKeywordReq){
		Member member = memberRepository.findByIdAndIsEnable(memberId,true).orElseThrow(
			MemberNotFoundException::new);
		Keyword keyword = keywordRepository.findByMember_IdAndNameAndIsEnable(
			memberId,
			postKeywordReq.getKeyword(), true).orElseThrow(NullPointerException::new);
		keyword.setIsEnable(false);
		member.deleteKeyword(keyword);
		return "success delete keyword";
	}

	@Transactional
	public MemberKeywordRes getMemberKeyword(Long memberId){
		List<Keyword> memberKeyword = keywordRepository.findByMember_IdAndIsEnable(memberId, true);
		List<String> keywordList =  memberKeyword.stream()
			.map(Keyword::getName)
			.collect(Collectors.toList());
		MemberKeywordRes memberKeywordRes = new MemberKeywordRes(keywordList);
		return memberKeywordRes;

	}



}
