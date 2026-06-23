package com.devcho.devchobackjpa.service;

import com.devcho.devchobackjpa.domain.UserInfo;
import com.devcho.devchobackjpa.dto.auth.AuthResponseDTO;
import com.devcho.devchobackjpa.repository.auth.AuthRepository;
import com.devcho.devchobackjpa.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final AuthRepository authRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtProvider jwtProvider;

    public AuthResponseDTO login(String loginId, String loginPassword) {
        UserInfo userInfo = authRepository.findUserInfoByUserId(loginId);

        String token = "";
        boolean loginResult = true;
        String failMsg = "로그인 되었습니다";
        if (userInfo == null) {
            loginResult = false;
            failMsg = "존재하지 않는 아이디 입니다";
        } else if (!bCryptPasswordEncoder.matches(loginPassword, userInfo.getPassword())) {
            loginResult = false;
            failMsg = "비밀번호가 일치하지 않습니다";
        } else {
            token = jwtProvider.createToken(userInfo.getId(), userInfo.getUserName());
        }
        return new AuthResponseDTO(token, userInfo, loginResult, failMsg);
    }
}
