package mmd.headless.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mmd.headless.dto.SmsRequest;
import mmd.headless.dto.NaverSmsResponse;
import mmd.headless.dto.SignatureDto;
import mmd.headless.util.NaverUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
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

        ResponseEntity<NaverSmsResponse> responseEntity = naverApiUtil(form, url, HttpMethod.POST, httpHeaders, NaverSmsResponse.class);
        
        // TODO 발송 결과 처리
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
    private <T> ResponseEntity<T> naverApiUtil(Object form, String url, HttpMethod method, HttpHeaders httpHeaders, Class<T> responseType) throws JsonProcessingException {
        HttpEntity<?> httpEntity = new HttpEntity<>(toJson(form), httpHeaders);

        try {
            ResponseEntity<T> response =
                    restTemplate.exchange(url, method, httpEntity,  responseType);

            return response;
        } catch (HttpClientErrorException httpClientErrorException){
            httpClientErrorException.printStackTrace();
            Map<String, Object> map = objectMapper.readValue(httpClientErrorException.getResponseBodyAsString(), Map.class);
            map.put("ErrorType", httpClientErrorException.getStatusText());
        } catch (Exception e){
            e.printStackTrace();
        }

        // TODO 발송 실패 처리
        return null;
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