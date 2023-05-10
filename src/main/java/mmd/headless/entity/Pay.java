package mmd.headless.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mmd.headless.enums.PayType;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pay extends BaseEntity{
    @Id
    @GeneratedValue
    @Column(name = "billing_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private PayType payType; // 결제 타입

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 회원
}
