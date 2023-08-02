package com.example.cherrypickserver.auth.application;

import com.example.cherrypickserver.auth.domain.CustomOAuth2User;
import com.example.cherrypickserver.auth.domain.SocialType;
import com.example.cherrypickserver.auth.dto.OAuthAttributes;
import com.example.cherrypickserver.member.domain.Member;
import com.example.cherrypickserver.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final static String ROLE = "MEMBER";

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        SocialType socialType = getSocialType(registrationId);
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        OAuthAttributes extractedAttributes = OAuthAttributes.of(socialType, userNameAttributeName, attributes);
        Member member = getMember(extractedAttributes);
        return new CustomOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(ROLE)),
                attributes,
                extractedAttributes.getNameAttributeKey(),
                member.getId(),
                member.getEmail());
    }

    private SocialType getSocialType(String registrationId) {
        SocialType type = SocialType.from(registrationId);
        if(type.isNaver()) {
            return SocialType.NAVER;
        }
        return SocialType.KAKAO;
    }

    private Member getMember(OAuthAttributes attributes) {
        Member member = memberRepository.findByEmail(attributes.getUserInfo().getEmail()).orElse(null);

        if(member == null) {
            return saveMember(attributes);
        }
        return member;
    }

    private Member saveMember(OAuthAttributes attributes) {
        Member member = attributes.toMember(attributes.getUserInfo());
        return memberRepository.save(member);
    }
}
