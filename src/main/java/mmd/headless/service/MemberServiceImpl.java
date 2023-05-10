package mmd.headless.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mmd.headless.dto.MemberRequest;
import mmd.headless.entity.Member;
import mmd.headless.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public Long newMember(MemberRequest form) {

        // 중복 검사
        validateDuplicateMember(form);

        // 가입
        Member member = Member.builder()
                .userPassword(form.getPassword())
                .username(form.getUsername())
                .email(form.getEmail())
                .phone(form.getPhone())
                .build();

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(MemberRequest member) {
        Optional<Member> members = memberRepository.findByUsernameAndEmail(member.getUsername(), member.getEmail());
        if(!members.isEmpty()){
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
    }
    
}
