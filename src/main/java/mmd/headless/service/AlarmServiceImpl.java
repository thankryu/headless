package mmd.headless.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mmd.headless.dto.SmsRequest;
import mmd.headless.dto.NaverSmsResponse;
import mmd.headless.dto.SignatureDto;
import mmd.headless.entity.AppLog;
import mmd.headless.repository.AppLogRepository;
import mmd.headless.util.NaverUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AlarmServiceImpl implements AlarmService {

    // 네이버 알림톡 서비스 아이디
    @Value("${naver.service.id}") String serviceId;
    // 네이버 알림톡 엑세스 키
    @Value("${naver.access.key}") String accessKey;
    // 네이버 send url
    @Value("${naver.sens.url}") String sendUrl;
    // 네이버 알림톡 SMS 공통 url
    @Value("${naver.sms.url}") String smsUrl;

    private final ObjectMapper objectMapper;

    private final RestTemplate restTemplate;

    private final AppLogRepository appLogRepository;

    /**
     * 네이버 문자 발송
     */
    @Override
    public void sendSMS(SmsRequest form) throws Exception {

        // 시그니처 키 생성
        String timestamp = String.valueOf(new Timestamp(System.currentTimeMillis()).getTime());

        String signatureUrl = smsUrl+serviceId+"/messages";

        SignatureDto dto = SignatureDto.builder()
                .url(signatureUrl)
                .method("POST")
                .timestamp(timestamp)
                .build();

        String signature = NaverUtil.makeSignature(dto);

        HttpHeaders httpHeaders = getHttpHeaders(timestamp, signature);

        // 발송 url
        String url = sendUrl + signatureUrl;

        String phone = form.getMessages().get(0).getTo();

        // API 발송
        ResponseEntity<NaverSmsResponse> responseEntity = naverApiUtil(form, url, HttpMethod.POST, httpHeaders, phone, NaverSmsResponse.class);
        
        // 발송 결과 저장
        AppLog appLog = AppLog.builder()
                .phone(phone)
                .statusName(responseEntity.getBody().getStatusName())
                .statusCode(responseEntity.getBody().getStatusCode())
                .requestId(responseEntity.getBody().getRequestId())
                .requestTime(responseEntity.getBody().getRequestTime())
                .resultMsg("Accept")
                .build();

        appLogRepository.save(appLog);
    }

    /**
     * 네이버 알림톡 공통 header 처리
     */
    private HttpHeaders getHttpHeaders(String timestamp, String signature) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Content-Type", "application/json; charset=utf-8");
        httpHeaders.set("x-ncp-apigw-timestamp", timestamp);
        httpHeaders.set("x-ncp-iam-access-key", accessKey);
        httpHeaders.set("x-ncp-apigw-signature-v2", signature);
        return httpHeaders;
    }

    /**
     * 네이버 알림톡 api 발송
     */
    private <T> ResponseEntity<T> naverApiUtil(Object form, String url, HttpMethod method, HttpHeaders httpHeaders, String phone ,Class<T> responseType) throws JsonProcessingException {
        HttpEntity<?> httpEntity = new HttpEntity<>(toJson(form), httpHeaders);
        Map<String, Object> resultMap = new HashMap<>();

        try {
            ResponseEntity<T> response =
                    restTemplate.exchange(url, method, httpEntity,  responseType);

            return response;
        } catch (HttpClientErrorException httpClientErrorException){
            httpClientErrorException.printStackTrace();
            resultMap = objectMapper.readValue(httpClientErrorException.getResponseBodyAsString(), Map.class);
            resultMap.put("resultMsg", httpClientErrorException.getStatusText());
            resultMap.put("statusCode", httpClientErrorException.getStatusCode());
            resultMap.put("statusName", "fail");
            saveAppErrorLog(phone, resultMap);
        } catch (Exception e){
            resultMap.put("resultMsg", "Exception");
            resultMap.put("statusCode", "500");
            resultMap.put("statusName", "fail");
            e.printStackTrace();
            saveAppErrorLog(phone, resultMap);
        }

        return null;
    }

    /**
     * 앱 에러 로그 저장
     * 차후에 Json Token 에서 App 정보 받을 수 있어야함
     */
    private void saveAppErrorLog(String phone, Map<String, Object> resultMap) {
        AppLog appLog = AppLog.appLogFail()
                .phone(phone)
                .statusName(String.valueOf(resultMap.get("statusName")))
                .statusCode(String.valueOf(resultMap.get("statusCode")))
                .resultMsg(String.valueOf(resultMap.get("resultMsg")))
                .build();

        appLogRepository.save(appLog);
    }

    /**
     * json 처리
     * @param data
     * @return
     * @param <T>
     * @throws JsonProcessingException
     */
    private <T> String toJson(T data) throws JsonProcessingException {
        return objectMapper.writeValueAsString(data);
    }

}