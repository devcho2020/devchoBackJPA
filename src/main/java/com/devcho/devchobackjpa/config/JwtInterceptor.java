package com.devcho.devchobackjpa.config;

import com.devcho.devchobackjpa.util.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtProvider jwtProvider;

    @Override
    public boolean preHandle(HttpServletRequest request
            , HttpServletResponse response
            , Object handler
    ) throws Exception {

        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);

            if (jwtProvider.validateToken(token)) {
                String userId = jwtProvider.getUserId(token);
                request.setAttribute("userId", userId);

                return true;
            }
        }

        log.info("=================================================");
        log.warn("[인터셉터 경보] 인증 실패로 튕겨나간 요청 정보");
        log.warn("-> 요청 URI : {}", request.getRequestURI());
        log.warn("-> HTTP 메서드 : {}", request.getMethod());
        log.warn("-> Authorization 헤더 : {}", bearerToken);
        log.warn("=================================================");

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "인증 유효기간 만료 또는 토큰없슴");
        return false;
    }
}
