package com.portfolio.trading.controller.member;

import com.portfolio.trading.data.dto.member.MemberResponseDto;
import com.portfolio.trading.data.dto.member.CreateMemberRequestDto;
import com.portfolio.trading.data.entity.member.Member;
import com.portfolio.trading.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping()
    public ResponseEntity<MemberResponseDto> createMember(@RequestBody CreateMemberRequestDto createMemberRequestDto) {
        MemberResponseDto memberResponseDto = memberService.createMember(createMemberRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(memberResponseDto);
    }

    @GetMapping("/me")
    public ResponseEntity<MemberResponseDto> getMe(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK).body(memberService.findByEmail(userDetails.getUsername()));
    }

}
