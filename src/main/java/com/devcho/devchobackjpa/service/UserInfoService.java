package com.devcho.devchobackjpa.service;

import com.devcho.devchobackjpa.domain.UserInfo;
import com.devcho.devchobackjpa.dto.page.PageResponse;
import com.devcho.devchobackjpa.dto.user.UserInfoRequestDTO;
import com.devcho.devchobackjpa.dto.user.UserInfoResponseDTO;
import com.devcho.devchobackjpa.repository.user.UserInfoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserInfoService {

    @PersistenceContext
    private EntityManager em;
    private final UserInfoRepository userInfoRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public PageResponse<UserInfoResponseDTO> searchUserInfo(int page, int size, Integer level, String searchValue) {
        Pageable pageable = PageRequest.of(page, size);

        Page<UserInfo> result = userInfoRepository.searchUserInfo(searchValue, level, pageable);

        List<UserInfoResponseDTO> content = result.getContent().stream()
                .map(UserInfoResponseDTO::from)
                .collect(Collectors.toList());

        return new PageResponse<>(content, result.getTotalPages(), result.getTotalElements());
    }

    public UserInfoResponseDTO findUserInfoById(Long userInfoId) {
        UserInfo userInfo = userInfoRepository
                .findById(userInfoId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자 입니다."));
        return UserInfoResponseDTO.from(userInfo);
    }

    @Transactional
    public Long saveUserInfo(UserInfoRequestDTO dto) {
        if (userInfoRepository.existsByUserId(dto.userId())) {
            throw new RuntimeException("이미 사용중인 아이디 입니다");
        }

        String encodedPassword = passwordEncoder.encode(dto.password());

        UserInfo userInfo = UserInfo.builder()
                .userId(dto.userId())
                .userName(dto.userName())
                .password(encodedPassword)
                .level(dto.level())
                .phone(dto.phone())
                .position(dto.position())
                .build();
        return userInfoRepository.save(userInfo).getId();
    }

    @Transactional
    public void updateUserInfo(Long userInfoId, UserInfoRequestDTO dto, Long sessionId) {
        UserInfo proxyUser = em.getReference(UserInfo.class, sessionId);
        UserInfo userInfo = userInfoRepository
                .findById(userInfoId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자 입니다."));

        userInfo.update(dto.userId(), dto.userName(), dto.position(), dto.level(), dto.phone(), proxyUser);
    }

    @Transactional
    public void updateUserInfoResetPassword(Long userInfoId, Long sessionId) {
        UserInfo proxyUser = em.getReference(UserInfo.class, sessionId);
        UserInfo userInfo = userInfoRepository
                .findById(userInfoId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자 입니다."));

        String encodedPassword = passwordEncoder.encode("1234");

        userInfo.updatePassword(encodedPassword, proxyUser);
    }

    public Map<String, Object> checkUserIdDuplication(String userId) {

        Map<String, Object> response = new HashMap<>();

        if (userInfoRepository.existsByUserId(userId)) {
            response.put("duplicationCheckOk", false);
            response.put("message", "이미 사용중인 아이디 입니다\n다른 아이디를 사용해 주세요");
        } else {
            response.put("duplicationCheckOk", true);
            response.put("message", "사용 가능한 아이디 입니다");
        }
        return response;
    }
}
