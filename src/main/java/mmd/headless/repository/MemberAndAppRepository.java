package mmd.headless.repository;

import mmd.headless.entity.MemberAndApp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberAndAppRepository extends JpaRepository<MemberAndApp, Long> {
}
