package mmd.headless.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppLog extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "app_log_id")
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="app_id")
//    private App app;

    private String phone;

    private String statusCode;

    private String resultMsg;

    private String statusName;

    private String requestId;
    private String requestTime;

    @Builder(builderMethodName = "appLogFail")
    public AppLog(String phone, String statusCode, String resultMsg, String statusName) {
        this.phone = phone;
        this.statusCode = statusCode;
        this.resultMsg = resultMsg;
        this.statusName = statusName;
    }

    @Builder
    public AppLog(String phone, String statusCode, String resultMsg, String statusName, String requestId, String requestTime) {
        this.phone = phone;
        this.statusCode = statusCode;
        this.resultMsg = resultMsg;
        this.statusName = statusName;
        this.requestId = requestId;
        this.requestTime = requestTime;
    }

    @Override
    public String toString() {
        return "AppLog{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", resultCode='" + statusCode + '\'' +
                ", resultMsg='" + resultMsg + '\'' +
                ", status='" + statusName + '\'' +
                '}';
    }
}