package com.portfolio.trading.data.dto.member;

import com.portfolio.trading.data.entity.member.Member;
import com.portfolio.trading.data.entity.member.Role;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberSignupRequestDto {

    private String email;
    private String password;
    private String username;
    private String provider;

    public Member toEntity(BCryptPasswordEncoder bCryptPasswordEncoder) {
        return Member.builder()
                .email(email)
                .password(bCryptPasswordEncoder.encode(password))
                .username(username)
                .role(Role.USER)
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .username(username)
                .role(Role.USER)
                .provider(provider)
                .build();
    }
}
