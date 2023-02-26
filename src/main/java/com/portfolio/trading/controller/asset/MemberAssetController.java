package com.portfolio.trading.controller.asset;

import com.portfolio.trading.data.dto.asset.MemberAssetDetailResponseDto;
import com.portfolio.trading.data.dto.asset.MemberAssetResponseDto;
import com.portfolio.trading.data.dto.asset.MemberKrwAssetResponseDto;
import com.portfolio.trading.data.dto.member.MemberResponseDto;
import com.portfolio.trading.service.asset.MemberAssetService;
import com.portfolio.trading.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/memberAssets")
public class MemberAssetController {

    private final MemberAssetService memberAssetService;
    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<List<MemberAssetResponseDto>> getMemberAssets(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        MemberResponseDto member = memberService.findByEmail(userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(memberAssetService.findAllByMemberId(member.getId()));
    }

    @GetMapping("/krw")
    public ResponseEntity<MemberKrwAssetResponseDto> getMemberKrwAsset(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        MemberResponseDto member = memberService.findByEmail(userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(memberAssetService.getMemberKrwAsset(member.getId()));
    }

    @GetMapping("/details")
    public ResponseEntity<List<MemberAssetDetailResponseDto>> getMemberAssetDetails(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        MemberResponseDto member = memberService.findByEmail(userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(memberAssetService.getMemberAssetDetail(member.getId()));
    }

}
