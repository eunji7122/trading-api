package com.portfolio.trading.data.dto.member;

import com.portfolio.trading.data.entity.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberRequestDto {

    private String email;
    private String username;

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .username(username).build();
    }
}
