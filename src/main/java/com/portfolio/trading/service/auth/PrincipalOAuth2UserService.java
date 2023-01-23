package com.portfolio.trading.service.auth;

import com.portfolio.trading.data.auth.GoogleUserInfo;
import com.portfolio.trading.data.auth.NaverUserInfo;
import com.portfolio.trading.data.auth.OAuth2UserInfo;
import com.portfolio.trading.data.auth.PrincipalDetails;
import com.portfolio.trading.data.entity.member.Member;
import com.portfolio.trading.data.entity.member.Role;
import com.portfolio.trading.data.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PrincipalOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo oAuth2UserInfo = createUserInfoByProvider(oAuth2User, provider);

        String providerId = oAuth2UserInfo.getProviderId();
        String username = oAuth2UserInfo.getName();
        String password = bCryptPasswordEncoder.encode("패스워드" + UUID.randomUUID().toString().substring(0, 6));
        String email = oAuth2UserInfo.getEmail();

        Optional<Member> findMember = memberRepository.findByEmail(email);

        // DB에 없는 사용자라면 회원가입 처리
        if(findMember.isEmpty()) {
            findMember = Optional.ofNullable(Member.oauth2Register()
                    .email(email)
                    .password(password)
                    .username(username)
                    .role(Role.USER)
                    .provider(provider)
                    .providerId(providerId)
                    .build());
            memberRepository.save(findMember.get());
        }
        return new PrincipalDetails(findMember.get(), oAuth2UserInfo);
    }

    private OAuth2UserInfo createUserInfoByProvider(OAuth2User oAuth2User, String provider) {
        if (provider.equals("google")) {
            return new GoogleUserInfo(oAuth2User.getAttributes());
        } else if (provider.equals("naver")) {
            return new NaverUserInfo(oAuth2User.getAttributes());
        }

        throw new RuntimeException();
    }
}
