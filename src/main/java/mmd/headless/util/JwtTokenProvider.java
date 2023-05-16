package mmd.headless.util;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Slf4j
@Component
public class JwtTokenProvider {

    // jwt 생성 암호화 키
    private static String JWT_SECRET;

    @Autowired
    public JwtTokenProvider(@Value("${jwt.secret-key}") String jwtSecretKey){
        this.JWT_SECRET = jwtSecretKey;
    }

    private static final long JWT_EXPIRATIONS_MS = 1000L * 60 * 60 * 24; // 24시간

    // JWT 토큰 생성
    public static String generateToken(Authentication authentication){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATIONS_MS);

        return Jwts.builder()
                .setClaims((Map) authentication.getPrincipal())
                .setIssuedAt(new Date()) // 현재 시간 기반으로 생성
                .setExpiration(expiryDate) // 만료 시간 셋팅
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET) // 사용 알고리즘, SECRET 값
                .compact();
    }

    // jwt 토큰에서 아이디 추출
    public static String getUserIdFromJWT(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        log.info("id:"+claims.getId());
        log.info("issuer:"+claims.getIssuer());
        log.info("issue:"+claims.getIssuedAt());
        log.info("subject:"+claims.getSubject());
        log.info("Audience:"+claims.getAudience());
        log.info("expire:"+claims.getExpiration().toString());
        log.info("userName:"+claims.get("userName"));
        log.info("userId:"+claims.get("userId"));

        return String.valueOf(claims.get("userId"));
    }

    // jwt 토큰 유효성 검사
    public static boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch (SignatureException e){
            log.error("Invalid JWT signature", e);
        } catch (MalformedJwtException e){
            log.error("Invalid JWT token", e);
        } catch (ExpiredJwtException e){
            log.error("Expired JWT token", e);
        } catch (UnsupportedJwtException e){
            log.error("Unsupported JWT token", e);
        } catch (IllegalArgumentException e){
            log.error("JWT claims string is empty", e);
        }
        return false;
    }
}