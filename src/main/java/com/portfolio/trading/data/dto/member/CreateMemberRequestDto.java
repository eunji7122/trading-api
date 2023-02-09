package com.portfolio.trading.data.dto.member;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateMemberRequestDto {

    private String email;
    private String password;
    private String username;
    private String provider;

}
