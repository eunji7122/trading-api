package com.portfolio.trading.controller.auth;

import com.portfolio.trading.data.dto.jwt.TokenDto;
import com.portfolio.trading.data.dto.member.CreateTokenRequestDto;
import com.portfolio.trading.service.auth.AuthService;
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
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/token")
    public ResponseEntity<TokenDto> createToken(@RequestBody CreateTokenRequestDto createTokenRequestDto) {
        TokenDto tokenDto = authService.createToken(createTokenRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(tokenDto);
    }
}
