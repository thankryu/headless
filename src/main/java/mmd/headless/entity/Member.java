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
public class Member extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;

    @OneToMany(mappedBy = "member")
    private List<MemberAndApp> memberAndApps = new ArrayList<>();

    public Member(String username) {
        this.username = username;
    }
}
