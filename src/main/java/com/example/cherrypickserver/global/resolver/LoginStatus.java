package com.example.cherrypickserver.global.resolver;

import lombok.Builder;
import lombok.Data;

@Data
public class LoginStatus {
    private Boolean isLogin;
    private Long memberId;
    @Builder
    public LoginStatus(Boolean isLogin, Long memberId) {
        this.isLogin = isLogin;
        this.memberId = memberId;
    }
    public static LoginStatus getNotLoginStatus() {
        return new LoginStatus(false, null);
    }
}
