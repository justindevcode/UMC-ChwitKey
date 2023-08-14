package com.example.cherrypickserver.member.dto.response;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Getter
@NoArgsConstructor
public class MemberKeywordRes {

	private List<String> keywordList;

	public void setKeywordList(List<String> keywordList) {
		this.keywordList = keywordList;
	}
}
