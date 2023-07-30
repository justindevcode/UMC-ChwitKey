package com.example.cherrypickserver.chat.domain;

import com.example.cherrypickserver.chat.exception.SelectTypeNotFoundException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum SelectType {

    SUMMARY("summary"),
    TRANSLATION("translation"),
    KEYWORD("keyword");

    private final String name;

    SelectType(String name) {
        this.name = name;
    }

    public static SelectType getSelectTypeByName(String name) {
        return Arrays.stream(SelectType.values())
                .filter(selectType -> selectType.getName().equals(name))
                .findAny().orElseThrow(SelectTypeNotFoundException::new);
    }
}
