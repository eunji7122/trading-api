package com.portfolio.trading.service.auth;

import com.portfolio.trading.config.security.JwtTokenProvider;
import com.portfolio.trading.data.dto.jwt.TokenDto;
import com.portfolio.trading.data.dto.jwt.TokenRequestDto;
import com.portfolio.trading.data.dto.member.MemberSigninRequestDto;
import com.portfolio.trading.data.dto.member.MemberSignupRequestDto;
import com.portfolio.trading.data.entity.member.Member;
import com.portfolio.trading.data.entity.security.RefreshToken;
import com.portfolio.trading.data.repository.member.MemberRepository;
import com.portfolio.trading.data.repository.security.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenDto createToken(MemberSigninRequestDto memberSigninRequestDto) {

        Member member = memberRepository.findByEmail(memberSigninRequestDto.getEmail()).orElse(null);

        if (!bCryptPasswordEncoder.matches(memberSigninRequestDto.getPassword(), member.getPassword())) {
            throw new RuntimeException();
        }

        TokenDto tokenDto = jwtTokenProvider.createToken(member.getEmail(), String.valueOf(member.getRole()));

        RefreshToken refreshToken = RefreshToken.builder()
                .key(member.getId())
                .token(tokenDto.getRefreshToken())
                .build();
        refreshTokenRepository.save(refreshToken);

        return tokenDto;
    }

    public Member createMember(MemberSignupRequestDto memberSignupRequestDto) {
        if (memberRepository.findByEmail(memberSignupRequestDto.getEmail()).isPresent()) {
            throw new RuntimeException();
        }
        return memberRepository.save(memberSignupRequestDto.toEntity(bCryptPasswordEncoder));
    }

    public TokenDto tokenReissue(TokenRequestDto tokenRequestDto) {
        // refresh token 만료 검사
        if (!jwtTokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException();
        }

        // 토큰으로 인증 정보 조회
        String accessToken = tokenRequestDto.getAccessToken();
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);

        // user 정보 조회
        Member member = memberRepository.findById(Long.parseLong(authentication.getName())).orElse(null);
        RefreshToken refreshToken = refreshTokenRepository.findByKey(member.getId()).orElse(null);

        // 리프레시 토큰 불일치 검사
        if (!refreshToken.getToken().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException();
        }

        // 토큰 재발급 및 저장
        TokenDto newCreatedToken = jwtTokenProvider.createToken(member.getEmail(), String.valueOf(member.getRole()));
        RefreshToken updateRefreshToken = refreshToken.updateToken(newCreatedToken.getRefreshToken());
        refreshTokenRepository.save(updateRefreshToken);

        return newCreatedToken;
    }
}
