package com.portfolio.trading.controller.auth;

import com.portfolio.trading.data.dto.jwt.TokenDto;
import com.portfolio.trading.data.dto.member.MemberResponseDto;
import com.portfolio.trading.data.dto.member.MemberSigninRequestDto;
import com.portfolio.trading.data.dto.member.MemberSignupRequestDto;
import com.portfolio.trading.data.entity.member.Member;
import com.portfolio.trading.service.security.SignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<TokenDto> signIn(@RequestBody MemberSigninRequestDto memberSigninRequestDto) {
        TokenDto tokenDto = signService.signIn(memberSigninRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(tokenDto);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<MemberResponseDto> signUp(@RequestBody MemberSignupRequestDto memberSignupRequestDto) {
        Member member = signService.signup(memberSignupRequestDto);
        MemberResponseDto memberResponseDto = new MemberResponseDto(member);
        return ResponseEntity.status(HttpStatus.OK).body(memberResponseDto);
    }
}
