package com.portfolio.trading.data.dto;

import com.portfolio.trading.data.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberRequestDto {

    private String email;
    private String username;

    @Builder
    public MemberRequestDto(String email, String name) {
        this.email = email;
        this.username = name;
    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .username(username).build();
    }
}
