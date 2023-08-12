package com.example.cherrypickserver.member.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateIndustryReq {
	private String memberNumber;
	private String industryKeyword1;
	private String industryKeyword2;
	private String industryKeyword3;
}
