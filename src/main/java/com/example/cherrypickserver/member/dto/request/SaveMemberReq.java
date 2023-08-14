package com.example.cherrypickserver.member.dto.request;

import com.example.cherrypickserver.member.domain.IndustryKeyword;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
public class SaveMemberReq {

	private String memberNumber;

	private String name;

	private String birthdate;

	private String gender;

	private String industryKeyword1;
	private String industryKeyword2;
	private String industryKeyword3;


}
