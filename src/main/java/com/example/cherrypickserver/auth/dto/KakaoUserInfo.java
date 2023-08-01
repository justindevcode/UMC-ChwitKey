package com.example.cherrypickserver.auth.dto;

import java.util.Map;

public class KakaoUserInfo extends UserInfo {

    public KakaoUserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");

        if (account == null) {
            return null;
        }

        return (String) account.get("email");
    }

    @Override
    public String getGender() {
        return (String) attributes.get("gender");
    }
}
