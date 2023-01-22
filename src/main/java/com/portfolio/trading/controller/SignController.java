package com.portfolio.trading.controller;

import com.portfolio.trading.data.dto.jwt.TokenDto;
import com.portfolio.trading.data.dto.member.MemberSigninRequestDto;
import com.portfolio.trading.data.dto.member.MemberSignupRequestDto;
import com.portfolio.trading.data.entity.member.Member;
import com.portfolio.trading.service.security.SignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/sign-api")
public class SignController {

    private final SignService signService;

    @PostMapping("/sign-in")
    public String signIn(@RequestBody MemberSigninRequestDto memberSigninRequestDto) {

        TokenDto tokenDto = signService.signIn(memberSigninRequestDto);
        return tokenDto.getAccessToken();
    }

    @PostMapping("/sign-up")
    public String signUp(@RequestBody MemberSignupRequestDto memberSignupRequestDto) {

        Member member = signService.signup(memberSignupRequestDto);
        return member.getEmail();
    }
}
