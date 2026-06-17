package com.devcho.devchobackjpa.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_info")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String password;

    private String userName;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 9")
    private Integer level;

    private Integer phone;

    private String position;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Builder
    public UserInfo(Long id, String userId, String userName, String password, String position, Integer level, Integer phone) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.position = position;
        this.level = level;
        this.phone = phone;
        this.createdAt = LocalDateTime.now();
    }

    public void update(String password, String position, Integer level, Integer phone) {
        this.password = password;
        this.position = position;
        this.level = level;
        this.phone = phone;
        this.updatedAt = LocalDateTime.now();
    }


}
