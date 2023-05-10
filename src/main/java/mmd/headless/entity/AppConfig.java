package mmd.headless.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppConfig extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "app_config_id")
    private Long id;

    private int allowAccessIpCnt; // IP 분당 요청 가능 횟수

    private int allowAccessPhoneCnt; // 전화번호당 분당 요청 가능 횟수

    private int cutRestart; // 차단 후 재설정 기간

    private String allowIp; // 허용 Host 목록
}
