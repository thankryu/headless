package mmd.headless.repository;

import mmd.headless.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUserEmail(String email);
    Optional<Member> findByEmailAndPassword(String username, String password);
}
