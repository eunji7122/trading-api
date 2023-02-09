package com.portfolio.trading.service.member;

import com.portfolio.trading.data.dto.member.MemberResponseDto;
import com.portfolio.trading.data.dto.member.CreateMemberRequestDto;
import com.portfolio.trading.data.dto.member.UpdateMemberRequestDto;
import com.portfolio.trading.data.entity.member.Member;
import com.portfolio.trading.data.entity.member.Role;
import com.portfolio.trading.data.repository.member.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MemberService {

    private MemberRepository memberRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public MemberResponseDto createMember(CreateMemberRequestDto createMemberRequestDto) {
        if (memberRepository.findByEmail(createMemberRequestDto.getEmail()).isPresent()) {
            throw new RuntimeException();
        }
        Member newMember = Member.builder()
                .email(createMemberRequestDto.getEmail())
                .password(bCryptPasswordEncoder.encode(createMemberRequestDto.getPassword()))
                .username(createMemberRequestDto.getUsername())
                .role(Role.USER)
                .build();
        return new MemberResponseDto(memberRepository.save(newMember));
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

    public Long getMemberId(String email) {
        Member findMember = memberRepository.findByEmail(email).orElse(null);
        return findMember.getId();
    }

    public MemberResponseDto updateMember(Long id, UpdateMemberRequestDto updateMemberRequestDto) {
        Member findMember = memberRepository.findById(id).orElse(null);
        findMember.setUsername(updateMemberRequestDto.getUsername());
        memberRepository.save(findMember);

        return new MemberResponseDto(findMember);
    }

}
