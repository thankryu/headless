package mmd.headless.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mmd.headless.enums.JoinType;

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

    private String username; // 사용자 이름

    private String email; // 사용자 이메일

    private String userPassword; // 사용자 비밀번호

    private String phone; // 사용자 전화번호

    @Enumerated(EnumType.STRING)
    private JoinType joinType; // 가입 타입

    @OneToMany(mappedBy = "member")
    private List<MemberAndApp> memberAndApps = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Pay> pays = new ArrayList<>(); // 결제 수단

    public Member(String username) {
        this.username = username;
    }

    @Builder
    public Member(String username, String email, String userPassword, String phone, JoinType joinType) {
        this.username = username;
        this.email = email;
        this.userPassword = userPassword;
        this.phone = phone;
        this.joinType = joinType;
    }
}
