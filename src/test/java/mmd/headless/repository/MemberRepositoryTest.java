package mmd.headless.repository;

import mmd.headless.entity.App;
import mmd.headless.entity.Member;
import mmd.headless.entity.MemberAndApp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    AppRepository appRepository;

    @Autowired
    MemberAndAppRepository memberAndAppRepository;

    @Autowired
    EntityManager em;

    /**
     * member, app relation table 테스트
     */
    @Test
    public void memberAndApp(){
        // given
        Member member1 = new Member("memberA");
        Member member2 = new Member("memberB");

        memberRepository.save(member1);
        memberRepository.save(member2);

        App app1 = new App("app1");
        App app2 = new App("app2");

        appRepository.save(app1);
        appRepository.save(app2);

        MemberAndApp memberAndApp1 = new MemberAndApp(member1, app1);
        MemberAndApp memberAndApp2 = new MemberAndApp(member2, app2);

        memberAndAppRepository.save(memberAndApp1);
        memberAndAppRepository.save(memberAndApp2);

        em.flush();
        em.clear();

        // when
        List<MemberAndApp> memberAndApps = memberAndAppRepository.findAll();

        for (MemberAndApp memberAndApp : memberAndApps) {
            System.out.println("memberAndApp Member=" +memberAndApp.getMember().getUsername());
            System.out.println("memberAndApp App=" +memberAndApp.getApp().getAppName());
        }

    }
}