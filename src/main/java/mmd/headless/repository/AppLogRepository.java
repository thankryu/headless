package mmd.headless.repository;

import mmd.headless.entity.AppLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppLogRepository extends JpaRepository<AppLog, Long> {
}
