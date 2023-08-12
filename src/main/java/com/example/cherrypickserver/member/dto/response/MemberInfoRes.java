package com.example.cherrypickserver.member.dto.response;

import com.example.cherrypickserver.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class MemberInfoRes {

	private String memberNumber;

	private String name;

	private String birthdate;

	private String gender;

	private String industryKeyword1;
	private String industryKeyword2;
	private String industryKeyword3;


	public static MemberInfoRes toDto(Member member){
		MemberInfoRes memberInfoRes = MemberInfoRes.builder()
			.memberNumber(member.getMemberNumber())
			.name(member.getName())
			.birthdate(member.getBirthdate())
			.gender(member.getGender())
			.industryKeyword1(member.getIndustryKeyword().getFirst() != null ? member.getIndustryKeyword().getFirst().getValue() : "")
			.industryKeyword2(member.getIndustryKeyword().getSecond() != null ? member.getIndustryKeyword().getSecond().getValue() : "")
			.industryKeyword3(member.getIndustryKeyword().getThird() != null ? member.getIndustryKeyword().getThird().getValue() : "")
			.build();
		return memberInfoRes;
	}

}
