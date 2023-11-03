package com.pjt.triptravel.member.service;

import com.pjt.triptravel.common.exception.DuplicateException;
import com.pjt.triptravel.member.dto.MemberRegisterParam;
import com.pjt.triptravel.member.entity.Member;
import com.pjt.triptravel.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(MemberRegisterParam param) {
        log.info("회원가입 서비스 실행............");

        if (!param.getPassword().equals(param.getPasswordConfirm())) {
            throw new IllegalArgumentException("입력한 비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }
        
        validateDuplicateEmail(param.getEmail());

        Member member = Member.builder()
                .email(param.getEmail())
                .password(param.getPassword())
                .name(param.getName())
                .age(param.getAge())
                .gender(param.getGender())
                .address(param.getAddress())
                .profileImageUrl(param.getProfileImageUrl())
                .build();
        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    private void validateDuplicateEmail(String email) {
        memberRepository.findByEmail(email)
                .ifPresent(m -> {
                    throw new DuplicateException("이미 등록된 이메일입니다.");
                });
    }

    public Member login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        if (!member.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return member;
    }
}
