package com.portfolio.trading.data.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMemberRequestDto {

    private String email;
    private String password;
    private String username;
}
