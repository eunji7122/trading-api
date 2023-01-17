package com.portfolio.trading.data.dto;

import com.portfolio.trading.data.entity.Member;
import lombok.Getter;

@Getter
public class MemberResponseDto {

    private final Long id;
    private final String email;
    private final String username;

    public MemberResponseDto(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.username = member.getUsername();
    }
}
