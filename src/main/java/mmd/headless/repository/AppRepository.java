package mmd.headless.repository;

import mmd.headless.entity.App;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRepository extends JpaRepository<App, Long> {
}
