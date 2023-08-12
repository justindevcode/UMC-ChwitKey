package com.example.cherrypickserver.member.dto.response;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class MemberKeywordRes {

	private List<String> keywordList;

}
