package mmd.headless;

import lombok.RequiredArgsConstructor;
import mmd.headless.entity.Member;
import mmd.headless.enums.JoinType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{

        private final EntityManager em;

        public void dbInit1(){
            Member member = Member.builder()
                    .username("hong123456789")
                    .phone("010-0000-0000")
                    .email("hong123456789@gmail.com")
                    .joinType(JoinType.valueOf("NORMAL"))
                    .password("123456789")
                    .build();

            Member member2 = Member.builder()
                    .username("ryu123456789")
                    .phone("010-0000-0001")
                    .email("ryu123456789@gmail.com")
                    .joinType(JoinType.valueOf("NORMAL"))
                    .password("ryu123456789")
                    .build();

            em.persist(member);
            em.persist(member2);
        }
    }
}
