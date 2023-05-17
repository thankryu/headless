package mmd.headless.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mmd.headless.Exception.BadRequestException;
import mmd.headless.dto.MemberLogin;
import mmd.headless.dto.MemberRequest;
import mmd.headless.dto.ResultResponse;
import mmd.headless.dto.TokenInfo;
import mmd.headless.entity.Member;
import mmd.headless.repository.MemberRepository;
import mmd.headless.util.EncryptUtil;
import mmd.headless.util.JwtTokenProvider;
import mmd.headless.util.UserAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    EncryptUtil encryptUtil = new EncryptUtil();

    @Transactional
    @Override
    public Long newMember(MemberRequest form) throws Exception {

        // 중복 검사
        validateDuplicateMember(form);

        // 가입
        Member member = Member.builder()
                .password(encryptUtil.encryptSHA512(form.getPassword()))
                .username(form.getUsername())
                .email(form.getEmail())
                .phone(form.getPhone())
                .build();

        memberRepository.save(member);
        return member.getId();
    }

    @Override
    public ResultResponse login(MemberLogin login) throws Exception {
        Optional<Member> userEntity = memberRepository.findByEmailAndPassword(login.getEmail(), encryptUtil.encryptSHA512(login.getPassword()));

        if(userEntity.isPresent()){
            Map<String, String> claims = new HashMap<>();
            claims.put("username", userEntity.get().getUsername());
            Authentication authentication = new UserAuthentication(claims, null, null);
            String token = JwtTokenProvider.generateToken(authentication);

            return new ResultResponse<>(TokenInfo.builder().grantType("Bearer").accessToken(token).build(), "success");
        } else {
            throw new BadRequestException("접속 정보를 확인해 주세요.");
        }
    }

    private void validateDuplicateMember(MemberRequest member) {
        Optional<Member> members = memberRepository.findByUserEmail(member.getEmail());
        if(!members.isEmpty()){
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
    }
    
}
