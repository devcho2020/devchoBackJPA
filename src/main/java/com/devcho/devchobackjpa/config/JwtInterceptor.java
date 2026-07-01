package com.devcho.devchobackjpa.config;

import com.devcho.devchobackjpa.util.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

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

        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);

            if (jwtProvider.validateToken(token)) {
                Long userInfoId = jwtProvider.getUserInfoId(token);
                request.setAttribute("userInfoId", userInfoId);
                return true;
            } else {
                sendErrorResponse(response, "ERR_TOKEN_EXPIRED", "로그인 정보가 만료 되었습니다\n다시 로그인 해주세요");
                return false;
            }
        }

        String reqURI = request.getRequestURI();
        String reqMethod = request.getMethod();
        log.info(reqMethod + " : " + reqURI);

        if (reqMethod.equals("OPTIONS")
            || reqMethod.equals("GET")) {
            return true;
        } else if ((reqMethod.equals("POST") && reqURI.startsWith("/api/user-info"))) { // 회원가입 시 필요
            return true;
        } else if (reqURI.startsWith("/api/auth/login")) { // 로그인
            return true;
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

    private void sendErrorResponse(HttpServletResponse response, String errorCode, String errorMessage) throws Exception {
        response.setStatus(401);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ObjectMapper obj = new ObjectMapper();

        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("status", 401);
        errorMap.put("errorCode", errorCode);
        errorMap.put("message", errorMessage);

        String jsonResult = obj.writeValueAsString(errorMap);

        response.getWriter().write(jsonResult);
    }
}
