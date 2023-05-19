package mmd.headless.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SmsRequest {

    private String type; // 발송 방법
    private String contentType; // 발송 타입
    private String from; // 발신 전화번호
    private String subject; // 문자 제목
    private String content; // 문자 내용
    private List<MessageRecipient> messages; // 수신자 목록

    @Getter
    @Setter
    @NoArgsConstructor
    public static class MessageRecipient {
        private String to; // 수신자 번화번호
    }

    @Override
    public String toString() {
        return "AlarmRequest{" +
                "type='" + type + '\'' +
                ", contentType='" + contentType + '\'' +
                ", from='" + from + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", messages=" + messages +
                '}';
    }
}


