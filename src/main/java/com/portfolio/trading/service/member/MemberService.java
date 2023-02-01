package com.portfolio.trading.service.member;

import com.portfolio.trading.data.dto.member.MemberRequestDto;
import com.portfolio.trading.data.dto.member.MemberResponseDto;
import com.portfolio.trading.data.dto.member.MemberSignupRequestDto;
import com.portfolio.trading.data.entity.member.Member;
import com.portfolio.trading.data.repository.member.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MemberService {

    private MemberRepository memberRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Member createMember(MemberSignupRequestDto memberSignupRequestDto) {
        if (memberRepository.findByEmail(memberSignupRequestDto.getEmail()).isPresent()) {
            throw new RuntimeException();
        }
        return memberRepository.save(memberSignupRequestDto.toEntity(bCryptPasswordEncoder));
    }

    public MemberResponseDto getMember(Long id) {
        Member member = memberRepository.getReferenceById(id);
        return new MemberResponseDto(member);
    }

    public MemberResponseDto findById(Long id) {
        Member findMember = memberRepository.findById(id).orElse(null);
        return new MemberResponseDto(findMember);
    }

    public MemberResponseDto findByEmail(String email) {
        Member findMember = memberRepository.findByEmail(email).orElse(null);
        return new MemberResponseDto(findMember);
    }

    public MemberResponseDto updateMember(Long id, MemberRequestDto memberRequestDto) {
        Member findMember = memberRepository.findById(id).orElse(null);
        findMember.setUsername(memberRequestDto.getUsername());
        memberRepository.save(findMember);

        return new MemberResponseDto(findMember);
    }

}
