package mmd.headless.util;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import mmd.headless.config.TokenFailDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("url check" + request.getRequestURI());

        if(!request.getRequestURI().contains("members")
                && !request.getRequestURI().contains("signup")
                && !request.getRequestURI().contains("swagger-ui")
                && !request.getRequestURI().contains("swagger")
                && !request.getRequestURI().contains("api-docs")
                && !request.getRequestURI().contains("/api/confirm")
                && !request.getRequestURI().contains("favicon")){
            try {
                String jwt = getJwtFromRequest(request);

                if(StringUtils.isNotEmpty(jwt) && JwtTokenProvider.validateToken(jwt)){
                    Claims claims = JwtTokenProvider.getUserIdFromJWT(jwt);

                    log.info("claims : " + claims);
                    UserAuthentication authentication = new UserAuthentication(claims.getId(), null, null);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    if(StringUtils.isNotEmpty(jwt)){
                        request.setAttribute("unAuthorization", new TokenFailDto("401", "인증키가 없습니다."));
                    }
                    if(JwtTokenProvider.validateToken(jwt)){
                        request.setAttribute("unAuthorization", new TokenFailDto("401", "인증키 만료되었습니다."));
                    }
                }
            } catch (Exception e){
                log.error("Could not set user authentication in security context", e);
                request.setAttribute("unAuthorization", new TokenFailDto("401", "인증 중 오류가 발생했습니다."));
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        log.info("bearerToken : "+ bearerToken);
        if(StringUtils.isNotEmpty(bearerToken) && bearerToken.startsWith("Bearer")){
            log.info("Bearer exist");
            return bearerToken.substring("Bearer ".length());
        }

        return null;
    }
}