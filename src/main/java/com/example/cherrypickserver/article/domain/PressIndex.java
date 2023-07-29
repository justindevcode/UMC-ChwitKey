package com.example.cherrypickserver.article.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity(name = "press_index")
@Table(name = "press_index")
public class PressIndex {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "press_index_id")
	private Long id;

	private String press;

	private String updateIndex;

	public PressIndex(String press, String updateIndex) {
		this.press = press;
		this.updateIndex = updateIndex;
	}

	public void setUpdateIndex(String updateIndex) {
		this.updateIndex = updateIndex;
	}
}
