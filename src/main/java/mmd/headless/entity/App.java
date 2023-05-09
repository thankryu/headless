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
public class App {

    @Id
    @GeneratedValue
    @Column(name = "app_id")
    private Long id;

    private String appName;

    @OneToMany(mappedBy = "app")
    private List<MemberAndApp> memberAndApps = new ArrayList<>();

    public App(String appName) {
        this.appName = appName;
    }
}