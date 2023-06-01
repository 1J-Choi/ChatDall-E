package com.ChatKarlo.ChatKarlo.service;

import com.ChatKarlo.ChatKarlo.entity.Member;
import com.ChatKarlo.ChatKarlo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public Member saveMember(Member member){
        checkNickMember(member);
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if (findMember != null) {
            throw new IllegalStateException("이미 가입 된 이메일입니다.");
        }
    }

    private void checkNickMember(Member member){
        Member findMeber = memberRepository.findByNick(member.getNick());
        if (findMeber != null){
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
        }
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            throw new UsernameNotFoundException(email);
        }

        return User.builder().username(member.getEmail()).password(member.getPassword()).authorities(Collections.emptyList()).build();
    }

    public Long findMemberId(String email){
        Long memberId = memberRepository.findByEmail(email).getId();
        return memberId;
    }
}
