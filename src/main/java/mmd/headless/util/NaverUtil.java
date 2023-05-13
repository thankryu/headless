package mmd.headless.util;

import lombok.extern.slf4j.Slf4j;
import mmd.headless.dto.SignatureDto;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@Component
@Slf4j
public class NaverUtil {

    private static String accessKey; // 네이버 엑세스 키
    private static String secretKey; // 네이버 시크릿 키

    public NaverUtil(@Value("${naver.access.key}") String accessKey,
                     @Value("${naver.secret.key}") String secretKey){
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public static String makeSignature(SignatureDto dto) throws Exception {
        String space = " ";					// one space
        String newLine = "\n";				// new line

        String message = new StringBuilder()
                .append(dto.getMethod())
                .append(space)
                .append(dto.getUrl())
                .append(newLine)
                .append(dto.getTimestamp())
                .append(newLine)
                .append(accessKey)
                .toString();

        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        String encodeBase64String = Base64.encodeBase64String(rawHmac);

        log.info("signature-v2::"+encodeBase64String);
        return encodeBase64String;
    }
}
