package mmd.headless.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class App extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "app_id")
    private Long id;

    private String appName;

    private String appContent;

    @OneToMany(mappedBy = "app")
    private List<MemberAndApp> memberAndApps = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "app_config_id")
    private AppConfig appConfig;

    @OneToMany(mappedBy = "app")
    private List<AppLog> appLog = new ArrayList<>();

    public App(String appName) {
        this.appName = appName;
    }
}