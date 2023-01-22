package com.portfolio.trading.data.dto.member;

import com.portfolio.trading.data.entity.member.Member;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberSigninRequestDto {

    private String email;
    private String password;

    public Member toMember(BCryptPasswordEncoder bCryptPasswordEncoder) {
        return Member.builder()
                .email(email)
                .password(bCryptPasswordEncoder.encode(password))
                .build();
    }
}
