package com.portfolio.trading.data.dto.member;

import com.portfolio.trading.data.entity.member.Member;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTokenRequestDto {

    private String email;
    private String password;

}
