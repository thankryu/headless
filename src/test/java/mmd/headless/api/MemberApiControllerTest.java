package mmd.headless.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mmd.headless.dto.MemberRequest;
import mmd.headless.entity.Member;
import mmd.headless.repository.MemberRepository;
import mmd.headless.service.MemberServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class MemberApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberApiController memberApiController;

    @Autowired
    MemberServiceImpl memberServiceImpl;

    @Autowired
    ObjectMapper objectMapper;

    /**
     * 회원가입 테스트
     */
    @Test
    public void memberNew() throws Exception {
        MemberRequest memberRequest = MemberRequest.builder()
                .username("thankryu4")
                .email("thankryu@gmail.com4")
                .phone("010-0000-0000")
                .password("abcd1234!@")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/members/new")
                        .content(toJson(memberRequest))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * 회원 중복 테스트
     */
    @Test
    public void duplicateMemberNew() throws Exception{
        Member member = Member.builder()
                .username("thankryu")
                .email("thankryu@gmail.com")
                .phone("010-0000-0000")
                .userPassword("abcd1234!@")
                .build();

        memberRepository.save(member);

        MemberRequest memberRequest = MemberRequest.builder()
                .username("thankryu")
                .email("thankryu@gmail.com")
                .phone("010-0000-0000")
                .password("abcd1234!@")
                .build();

        assertThrows(IllegalArgumentException.class, () -> {
            memberServiceImpl.newMember(memberRequest);
        });
    }

    private <T> String toJson(T data) throws JsonProcessingException {
        return objectMapper.writeValueAsString(data);
    }
}