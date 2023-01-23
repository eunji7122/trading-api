package com.portfolio.trading.data.dto.member;

import com.portfolio.trading.data.entity.member.Member;
import com.portfolio.trading.data.entity.member.Role;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class MemberResponseDto {

    private final Long id;
    private final String email;
    private final String username;
    private Role role;
//    private Collection<? extends GrantedAuthority> authorities;

    public MemberResponseDto(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.username = member.getUsername();
        this.role = member.getRole();
//        this.authorities = member.getAuthorities();
    }
}
