package mmd.headless.api;

import mmd.headless.dto.SmsRequest;
import mmd.headless.dto.SignatureDto;
import mmd.headless.entity.AppLog;
import mmd.headless.repository.AppLogRepository;
import mmd.headless.service.AlarmServiceImpl;
import mmd.headless.util.NaverUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

@SpringBootTest
@Transactional
class AlarmControllerTest {

    @Value("${naver.access.key}") String accessKey;
    @Value("${naver.service.id}") String serviceId;
    @Value("${naver.sendNumber}") String sendNumber;
    private String url = "/sms/v2/services/";

    @Autowired
    AlarmServiceImpl alarmService;

    @Autowired
    AppLogRepository appLogRepository;

    /**
     * 네이버 발송 시그니처 키 생성 테스트
     */
    @Test
    public void makeSignature() throws Exception {

        String timestamp = String.valueOf(new Timestamp(System.currentTimeMillis()).getTime());

        String keyUrl = url + serviceId +"/messages";

        SignatureDto dto = SignatureDto.builder()
                .method("POST")
                .url(keyUrl)
                .timestamp(timestamp)
                .build();

        String signature = NaverUtil.makeSignature(dto);
        System.out.println("signature::"+signature);
    }

    /**
     * 문자 발송 테스트
     */
    @Test
    @Rollback(value = false)
    public void sendSMS() throws Exception{

        // 발송 정보 저장
        SmsRequest form = new SmsRequest();
        form.setType("SMS");
        form.setFrom(sendNumber);
        form.setSubject("문자 제목");
        form.setContent("문자 내용");
        form.setContentType("COMM");

        SmsRequest.MessageRecipient messageRecipient = new SmsRequest.MessageRecipient();
        messageRecipient.setTo("01000000000");
        form.setMessages((Arrays.asList(messageRecipient)));

        // 발송
        alarmService.sendSMS(form);

        // 발송결과 확인
        List<AppLog> appLogs = appLogRepository.findAll();

        for (AppLog appLog : appLogs) {
            System.out.println(appLog.toString());
        }
    }
}