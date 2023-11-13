package com.pjt.triptravel.member.service;

import com.pjt.triptravel.common.exception.UserNotFoundException;
import com.pjt.triptravel.member.dto.MemberDto;
import com.pjt.triptravel.member.dto.MemberSimpleDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pjt.triptravel.common.exception.DuplicateException;
import com.pjt.triptravel.member.dto.MemberRegisterParam;
import com.pjt.triptravel.member.entity.Member;
import com.pjt.triptravel.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberDto findOne(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return MemberDto.of(member);
    }

    public MemberSimpleDto findSimple(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return MemberSimpleDto.of(member);
    }

    @Transactional
    public Long join(MemberRegisterParam param) {
        log.info("회원가입 서비스 실행............");

        if (!param.getPassword().equals(param.getPasswordConfirm())) {
            throw new IllegalArgumentException("입력한 비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        validateDuplicateEmail(param.getEmail());
        validateDuplicateNickname(param.getNickname());

        Member member = Member.builder()
                .email(param.getEmail())
                .password(passwordEncoder.encode(param.getPassword()))
                .name(param.getName())
                .nickname(param.getNickname())
                .age(param.getAge())
                .gender(param.getGender())
                .address(param.getAddress())
                .profileImageUrl(param.getProfileImageUrl())
                .build();
        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    public void validateDuplicateEmail(String email) {
        memberRepository.findByEmail(email)
                .ifPresent(m -> {
                    throw new DuplicateException("이미 등록된 이메일입니다.");
                });
    }

    public void validateDuplicateNickname(String nickname) {
        memberRepository.findByNickname(nickname)
                .ifPresent(m -> {
                    throw new DuplicateException("이미 등록된 닉네임입니다.");
                });
    }
}
